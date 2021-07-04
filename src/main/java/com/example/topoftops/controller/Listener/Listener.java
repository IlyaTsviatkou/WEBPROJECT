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
        CustomConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        CustomConnectionPool.getInstance().destroyPool();
    }
}
