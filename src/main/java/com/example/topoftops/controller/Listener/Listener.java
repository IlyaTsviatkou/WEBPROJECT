package com.example.topoftops.controller.Listener;

import com.example.topoftops.exception.ConnectionPoolException;
import com.example.topoftops.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application listener
 *
 * @author Ilya Tsvetkov
 * @see ServletContextListener
 */
public class Listener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            CustomConnectionPool.getInstance();
            logger.log(Level.INFO, "connection pool successfully created");
        } catch (RuntimeException e) {
            logger.log(Level.ERROR, "couldn't create connection pool", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            CustomConnectionPool.getInstance().destroyPool();
            logger.log(Level.INFO, "connection pool successfully destroyed");
        } catch (RuntimeException e) {
            logger.log(Level.ERROR, "couldn't destroy connection pool", e);
            throw new RuntimeException(e);
        }
    }
}
