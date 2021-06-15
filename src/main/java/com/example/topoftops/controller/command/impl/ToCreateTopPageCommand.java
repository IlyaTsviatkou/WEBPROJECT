package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.entity.CustomUser;
import com.example.topoftops.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class ToCreateTopPageCommand implements Command {
    private static final String ATTRIBUTE_NAME_USER = "user";
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page ;
        CustomUser user = (CustomUser) request.getSession().getAttribute(ATTRIBUTE_NAME_USER);
        if(user != null) {
            page = ConfigurationManager.getProperty(PagePath.CREATE_TOP);
        } else {
            page = ConfigurationManager.getProperty(PagePath.INDEX);
        }
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
