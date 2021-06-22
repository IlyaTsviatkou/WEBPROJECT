package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.controller.command.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class ToRegisterPageCommand implements Command {
    public ToRegisterPageCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page = ConfigurationManager.getProperty(PagePath.REGISTER);
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
