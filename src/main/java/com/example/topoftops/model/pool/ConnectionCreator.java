package com.example.topoftops.model.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The ConnectionCreator is responsible for for creating connections
 *
 * @author Ilya Tsvetkov
 */
final class ConnectionCreator {
    private static Logger logger = LogManager.getLogger();
    private static final String BUNDLE_NAME = "database";
    private static final String DB_DRIVER = "db.driver";
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DATABASE_URL;
    private static final String DATABASE_USER_NAME;
    private static final String DATABASE_PASSWORD;
    static final ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle(BUNDLE_NAME);
        String driverName = bundle.getString(DB_DRIVER);
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL, "driver wasn't found", e);
        }
        DATABASE_URL = bundle.getString(DB_URL);
        DATABASE_USER_NAME = bundle.getString(DB_USER);
        DATABASE_PASSWORD = bundle.getString(DB_PASSWORD);
    }

    private ConnectionCreator() {
    }

    /**
     * Creates a connection to the database
     *
     * @return {@link Connection} connection to the database
     * @throws SQLException
     */
    static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER_NAME, DATABASE_PASSWORD);
    }
}
