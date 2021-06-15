package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.entity.CustomUser;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.resource.ConfigurationManager;
import com.example.topoftops.model.service.SortService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class FindAllUsersCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_NAME_USER = "users";

    private SortService sortService;

    public FindAllUsersCommand(SortService sortService) {
        this.sortService = sortService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page;
        ArrayList<CustomUser> users = null;
        try {
            users = (ArrayList<CustomUser>) sortService.findAllUsers();
            request.setAttribute(ATTRIBUTE_NAME_USER, users);
            page = ConfigurationManager.getProperty(PagePath.SORT);
        } catch (ServiceException e) {
            logger.log(Level.ERROR,e);
            page = ConfigurationManager.getProperty(PagePath.INDEX);
        }

        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
