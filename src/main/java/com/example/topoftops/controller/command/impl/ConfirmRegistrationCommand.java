package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.*;
import com.example.topoftops.entity.User;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.topoftops.controller.command.RequestParam.PARAM_ERROR_MESSAGE;
import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_USER;

/**
 * The command is responsible for confirming account
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class ConfirmRegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private UserService userService;

    public ConfirmRegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String userId = request.getParameter(PARAM_NAME_USER);
        String page = ConfigurationManager.getProperty(PagePath.ERROR);
        try {
            if (userService.activate(userId)) {
                User user = (User) request.getSession().getAttribute("user");
                if (user != null) {
                    user.setStatus(1);
                    request.getSession().setAttribute("user", user);
                }
                page = ConfigurationManager.getProperty(PagePath.INDEX);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Couldnt confirm acc for user", e);
            request.getSession().setAttribute(PARAM_ERROR_MESSAGE,"Couldnt confirm acc for user");
        }
        router = new Router(page, Router.RouteType.REDIRECT);
        return router;
    }
}
