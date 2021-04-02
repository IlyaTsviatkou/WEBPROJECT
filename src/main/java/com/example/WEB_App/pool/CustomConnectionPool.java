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

public class CustomConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceBundle bundle;
    private static final String BUNDLE_NAME = "database";
    private static final String DB_POOLSIZE = "db.poolsize";
    public static final String DB_DRIVER = "db.driver";
    public static final String DB_URL = "db.url";
    public static final String DB_USER = "db.user";
    public static final String DB_PASSWORD = "db.password";
    private static final String DATABASE_URL;
    private static final String DATABASE_USER_NAME;
    private static final String DATABASE_PASSWORD;
    private static final int POOL_SIZE;
    private static CustomConnectionPool instance = null;
    private BlockingQueue<Connection> freeConnection;
    private Queue<Connection> givenAwayConnection;

    static {
        bundle = ResourceBundle.getBundle(BUNDLE_NAME);
        POOL_SIZE = Integer.parseInt(bundle.getString(DB_POOLSIZE));
        DATABASE_URL = bundle.getString(DB_URL);
        DATABASE_USER_NAME = bundle.getString(DB_USER);
        DATABASE_PASSWORD = bundle.getString(DB_PASSWORD);
    }

    CustomConnectionPool () {
        String driverName = bundle.getString(DB_DRIVER);
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL, "driver don't found", e);
            e.printStackTrace();
        }

        freeConnection = new LinkedBlockingDeque<>(POOL_SIZE);
        givenAwayConnection = new ArrayDeque<Connection>();
        try {
            for (int i = 0; i < POOL_SIZE; i++) {
                freeConnection.add(DriverManager.getConnection(DATABASE_URL, DATABASE_USER_NAME, DATABASE_PASSWORD));
            }
        } catch (SQLException e) {
            logger.log(Level.WARN,"cant connect with db");
        }

    }

    public static CustomConnectionPool getInstance() throws SQLException {
        if(instance == null) {
            instance = new CustomConnectionPool();
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
        if(connection.getClass() == ProxyConnection.class) {
            givenAwayConnection.remove(connection);
            freeConnection.offer(connection);
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
