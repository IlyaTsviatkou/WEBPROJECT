package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The command is responsible for logout
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class LogoutCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_NAME_USER = "user";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page;
        request.setAttribute(ATTRIBUTE_NAME_USER, null);
        request.getSession().setAttribute(ATTRIBUTE_NAME_USER, null);
        page = ConfigurationManager.getProperty(PagePath.INDEX);
        router = new Router(page, Router.RouteType.REDIRECT);
        return router;
    }
}
