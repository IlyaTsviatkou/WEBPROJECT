package com.example.WEB_App.controller.command.impl;

import com.example.WEB_App.controller.command.Command;
import com.example.WEB_App.controller.command.Router;
import com.example.WEB_App.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class ToRegisterPageCommand implements Command {
    public ToRegisterPageCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page = ConfigurationManager.getProperty("path.page.register");
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
