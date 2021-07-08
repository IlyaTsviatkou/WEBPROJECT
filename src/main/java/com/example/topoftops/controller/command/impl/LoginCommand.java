package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.entity.User;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.controller.command.ConfigurationManager;
import com.example.topoftops.model.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.example.topoftops.controller.command.RequestParam.*;

/**
 * The command is responsible for login
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_NAME_USER = "user";
    private UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        Optional<User> user;
        page = ConfigurationManager.getProperty(PagePath.ERROR);
        try {
            user = userService.login(login, pass);
            if (user.isPresent()) {
                request.setAttribute(ATTRIBUTE_NAME_USER, user.get());
                request.getSession().setAttribute(ATTRIBUTE_NAME_USER, user.get());
                page = ConfigurationManager.getProperty(PagePath.INDEX);
            } else {
                request.getSession().setAttribute(PARAM_ERROR_MESSAGE, "Incorrect login or password.");
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(PARAM_ERROR_MESSAGE, "any problem with authorisation");

        }

        router = new Router(page, Router.RouteType.REDIRECT);
        return router;
    }
}
