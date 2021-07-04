package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.controller.command.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * The command is responsible for login page
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class ToLoginPageCommand implements Command {
    public ToLoginPageCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page = ConfigurationManager.getProperty(PagePath.LOGIN);
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
