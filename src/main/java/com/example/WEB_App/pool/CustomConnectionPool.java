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
    private static final int POOL_SIZE;
    private static CustomConnectionPool instance = new CustomConnectionPool();
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
                Connection connection = ConnectionCreator.getConnection();
                freeConnection.add(connection);
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
