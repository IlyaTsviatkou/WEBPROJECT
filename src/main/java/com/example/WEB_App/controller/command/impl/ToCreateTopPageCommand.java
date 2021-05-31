package com.example.WEB_App.controller.command.impl;

import com.example.WEB_App.controller.command.Command;
import com.example.WEB_App.controller.command.Router;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class ToCreateTopPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page ;
        CustomUser user = (CustomUser) request.getSession().getAttribute("user");
        if(user != null) {
            page = ConfigurationManager.getProperty("path.page.create_top");
        } else {
            page = ConfigurationManager.getProperty("path.index");
        }
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
