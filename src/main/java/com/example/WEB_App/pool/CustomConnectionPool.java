package com.example.WEB_App.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomConnectionPool { //fixme add some logs
    private static final Logger logger = LogManager.getLogger();
    private static boolean isCreated;
    private static Lock locker = new ReentrantLock(true);
    private static final ResourceBundle bundle;
    private static final String BUNDLE_NAME = "database";
    private static final String DB_POOLSIZE = "db.poolsize";
    private static final int POOL_SIZE;
    private static CustomConnectionPool instance = null;
    private BlockingQueue<Connection> freeConnection;
    private Queue<Connection> givenAwayConnection;

    static {
        bundle = ResourceBundle.getBundle(BUNDLE_NAME);
        POOL_SIZE = Integer.parseInt(bundle.getString(DB_POOLSIZE));
    }


    CustomConnectionPool () {
        freeConnection = new LinkedBlockingDeque<>(POOL_SIZE);
        givenAwayConnection = new ArrayDeque<Connection>();
        try {
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = ConnectionCreator.createConnection();
                freeConnection.add(connection);
            }
        } catch (SQLException e) {
            logger.log(Level.WARN,"cant connect with db");
        }
    }

    public static CustomConnectionPool getInstance() throws SQLException {
        if(!isCreated) {
            locker.lock();
            if(instance == null) {
                instance = new CustomConnectionPool();
                isCreated=true;
            }
            locker.unlock();
        }
        return instance;
    }


    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnection.take();
            givenAwayConnection.offer(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if(connection.getClass() == ProxyConnection.class && givenAwayConnection.remove(connection)) {
            try {
                freeConnection.put(connection);
            } catch (InterruptedException e) {
                logger.log(Level.WARN,e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void destroyPool() {
        for (int i=0; i < POOL_SIZE; i++ ) {
            try {
               ProxyConnection connection = (ProxyConnection) freeConnection.take();
               connection.reallyClose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        deregisterDrivers();
    }

    public void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            }catch (SQLException e) {

            }
        });
    }




}
