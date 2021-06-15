package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLocalCommand implements Command {
    public ChangeLocalCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page = ConfigurationManager.getProperty(PagePath.INDEX); //fixme mby try to take url from session
        HttpSession session = request.getSession();
        String lang = request.getParameter("language");
        if(lang.equals("rus")) {
            session.setAttribute("locale","ru_RU");
        } else if (lang.equals("eng")) {
            session.setAttribute("locale","en_US");
        }
        router = new Router(page, Router.RouteType.REDIRECT);
        return router;
    }
}
