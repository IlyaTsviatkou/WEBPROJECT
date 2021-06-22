package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.controller.command.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class NoCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page = ConfigurationManager.getProperty(PagePath.INDEX);
        router = new Router(page, Router.RouteType.REDIRECT);
        return router;
    }
}
