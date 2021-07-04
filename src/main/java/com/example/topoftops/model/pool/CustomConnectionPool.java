package com.example.topoftops.model.pool;

import com.example.topoftops.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.example.topoftops.model.pool.ConnectionCreator.bundle;

/**
 * Pool of connections used while the system is running
 *
 * @author Ilya Tsvetkov
 */
public class CustomConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static boolean isCreated;
    private static Lock locker = new ReentrantLock(true);
    private static final String BUNDLE_NAME = "database";
    private static final String DB_POOLSIZE = "db.poolsize";
    private static final int POOL_SIZE;
    private static CustomConnectionPool instance;
    private BlockingQueue<ProxyConnection> freeConnection;
    private BlockingQueue<ProxyConnection> givenAwayConnection;

    static {
        POOL_SIZE = Integer.parseInt(bundle.getString(DB_POOLSIZE));
    }


    CustomConnectionPool() {
        freeConnection = new LinkedBlockingDeque<>(POOL_SIZE);
        givenAwayConnection = new LinkedBlockingDeque<>(POOL_SIZE);
        try {
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = ConnectionCreator.createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnection.add(proxyConnection);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "cant connect with db" + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets instance of this class
     *
     * @return {@link CustomConnectionPool} instance
     */
    public static CustomConnectionPool getInstance() {
        if (!isCreated) {
            try {
                locker.lock();
                if (instance == null) {
                    instance = new CustomConnectionPool();
                    isCreated = true;
                }
            } finally {
                locker.unlock();
            }
        }
        return instance;
    }

    /**
     * Gets a connection from the connection pool
     *
     * @return {@link Connection} connection to the database
     * @throws ConnectionPoolException if {@link InterruptedException} occurs
     */
    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnection.take();
            givenAwayConnection.put(connection);
        } catch (InterruptedException e) {
            logger.error("Thread was interrupted, " + e.getMessage());
        }
        return connection;
    }

    /**
     * Returns the connection to the connection pool
     *
     * @param connection {@link Connection} connection to the database
     */
    public void releaseConnection(Connection connection) {
        if (givenAwayConnection.remove(connection)) {
            try {
                freeConnection.put((ProxyConnection) connection);
            } catch (InterruptedException e) {
                logger.log(Level.WARN, e.getMessage());
            }
        }
    }

    /**
     * Destroy connection pool
     *
     * @throws ConnectionPoolException if {@link InterruptedException} or
     *                                 {@link SQLException} occurs
     */
    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                ProxyConnection connection = (ProxyConnection) freeConnection.take();
                connection.reallyClose();
            } catch (InterruptedException | SQLException e) {
                logger.log(Level.WARN, e.getMessage());
            }
        }
        deregisterDrivers();
    }

    /**
     * Unregisters drivers
     *
     * @throws ConnectionPoolException if {@link SQLException} occurs
     */
    public void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.log(Level.FATAL, "driver deregistration error", e);
            }
        }


    }
}
