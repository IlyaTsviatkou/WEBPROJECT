package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.controller.command.ConfigurationManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The command is responsible for invalid command by user
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class NoCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        logger.log(Level.WARN, "tried get non-existent command");
        Router router;
        String page = ConfigurationManager.getProperty(PagePath.INDEX);
        router = new Router(page, Router.RouteType.REDIRECT);
        return router;
    }
}
