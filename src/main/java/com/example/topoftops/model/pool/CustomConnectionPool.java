package com.example.topoftops.model.pool;

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

public class CustomConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static boolean isCreated;
    private static Lock locker = new ReentrantLock(true);
    private static final String BUNDLE_NAME = "database";
    private static final String DB_POOLSIZE = "db.poolsize";
    private static final int POOL_SIZE;
    private static CustomConnectionPool instance;
    private BlockingQueue<Connection> freeConnection;
    private BlockingQueue<Connection> givenAwayConnection;

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


    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnection.take();
            givenAwayConnection.put(connection);
        } catch (InterruptedException e) {
            logger.error("Thread was interrupted, " + e.getMessage());
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection.getClass() == ProxyConnection.class && givenAwayConnection.remove(connection)) {
            try {
                freeConnection.put(connection);
            } catch (InterruptedException e) {
                logger.log(Level.WARN, e.getMessage());
            }
        }
    }

    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                ProxyConnection connection = (ProxyConnection) freeConnection.take();
                connection.reallyClose();
            } catch (InterruptedException e) {
                logger.log(Level.WARN, e.getMessage());
            }
        }
        deregisterDrivers();
    }

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
