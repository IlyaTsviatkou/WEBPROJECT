package com.example.WEB_App.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionCreator {
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceBundle bundle;
    private static final String BUNDLE_NAME = "database";
    public static final String DB_DRIVER = "db.driver";
    public static final String DB_URL = "db.url";
    public static final String DB_USER = "db.user";
    public static final String DB_PASSWORD = "db.password";
    private static final String DATABASE_URL;
    private static final String DATABASE_USER_NAME;
    private static final String DATABASE_PASSWORD;
    private static final String DATABASE_DRIVER;


    static {
        bundle = ResourceBundle.getBundle(BUNDLE_NAME);
        DATABASE_URL = bundle.getString(DB_URL);
        DATABASE_USER_NAME = bundle.getString(DB_USER);
        DATABASE_PASSWORD = bundle.getString(DB_PASSWORD);
        DATABASE_DRIVER = bundle.getString(DB_DRIVER);
        try {
            Class.forName(DATABASE_DRIVER);
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL,"driver couldnt find");
            e.printStackTrace();
        }
    }

    private ConnectionCreator(){}

    public static Connection getConnection() throws SQLException {
       return DriverManager.getConnection(DATABASE_URL, DATABASE_USER_NAME, DATABASE_PASSWORD);
    }
}
