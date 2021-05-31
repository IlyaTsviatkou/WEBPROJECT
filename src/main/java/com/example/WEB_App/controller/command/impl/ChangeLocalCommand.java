package com.example.WEB_App.controller.command.impl;

import com.example.WEB_App.controller.command.Command;
import com.example.WEB_App.controller.command.Router;
import com.example.WEB_App.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLocalCommand implements Command {
    public ChangeLocalCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page = ConfigurationManager.getProperty("path.index"); //fixme mby try to take url from session
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
