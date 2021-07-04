package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.*;
import com.example.topoftops.entity.User;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_LOGIN;

/**
 * The command is responsible for find user by login
 *
 * @author Ilya Tsvetkov
 * @see AjaxCommand
 */
public class FindUsersByLoginAjaxCommand implements AjaxCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_NAME_DATA = "data";

    private UserService userService;

    public FindUsersByLoginAjaxCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request) {
        ArrayList<User> users = null;
        try {
            String login = request.getParameter(PARAM_NAME_LOGIN);
            users = (ArrayList<User>) userService.findUsersByName(login);
            request.setAttribute(ATTRIBUTE_NAME_DATA, users);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }

    }
}
