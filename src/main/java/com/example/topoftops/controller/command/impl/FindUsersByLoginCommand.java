package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.entity.CustomUser;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.controller.command.ConfigurationManager;
import com.example.topoftops.model.service.AdminService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class FindUsersByLoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_NAME_USER = "users";

    private AdminService sortService;

    public FindUsersByLoginCommand(AdminService sortService) {
        this.sortService = sortService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page;
        ArrayList<CustomUser> users = null;
        try {
            String login = request.getParameter("login");
            users = (ArrayList<CustomUser>) sortService.findUsersByName(login);
            request.setAttribute(ATTRIBUTE_NAME_USER, users);
            page = ConfigurationManager.getProperty(PagePath.ADMIN_PANEL);
        } catch (ServiceException e) {
            logger.log(Level.ERROR,e);
            page = ConfigurationManager.getProperty(PagePath.INDEX);
        }

        router = new Router(page, Router.RouteType.FORWARD);//fixme if i find how to fix problem with back and F5 will fix it
        return router;
    }
}