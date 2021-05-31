package com.example.WEB_App.controller.command.impl;

import com.example.WEB_App.controller.command.Command;
import com.example.WEB_App.controller.command.Router;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.exception.DaoException;
import com.example.WEB_App.resource.ConfigurationManager;
import com.example.WEB_App.model.service.SortService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class FindAllUsersCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_NAME_USER = "users";

    SortService sortService;

    public FindAllUsersCommand(SortService sortService) {
        this.sortService = sortService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page = null;
        ArrayList<CustomUser> users = null;
        try {
            users = (ArrayList<CustomUser>) sortService.findAllUsers();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        request.setAttribute(ATTRIBUTE_NAME_USER, users);
        page = ConfigurationManager.getProperty("path.page.sort");
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
