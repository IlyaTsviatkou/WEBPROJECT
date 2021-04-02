package com.example.WEB_App.command.impl;

import com.example.WEB_App.command.Command;
import com.example.WEB_App.comparator.UserComparator;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.exception.DaoException;
import com.example.WEB_App.resource.ConfigurationManager;
import com.example.WEB_App.resource.MessageManager;
import com.example.WEB_App.service.LoginService;
import com.example.WEB_App.service.SortService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

public class FindAllUsersCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_NAME_USER = "users";

    SortService sortService;

    public FindAllUsersCommand(SortService sortService) {
        this.sortService = sortService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        ArrayList<CustomUser> users = null;
        try {
            users = (ArrayList<CustomUser>) sortService.findAllUsers();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        request.setAttribute(ATTRIBUTE_NAME_USER, users);
        page = ConfigurationManager.getProperty("path.page.sort");
        return page;
    }
}
