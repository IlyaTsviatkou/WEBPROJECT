package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.UserService;
import com.example.topoftops.controller.command.ConfigurationManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_USER;

public class ConfirmRegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private UserService userService;
    public ConfirmRegistrationCommand(UserService userService) {
        this.userService = userService;
    }
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String userId = request.getParameter(PARAM_NAME_USER);//fixme add message Error and etc.
        String page = ConfigurationManager.getProperty(PagePath.INDEX);;
        try {
            if (userService.activate(userId)) {
                page = ConfigurationManager.getProperty(PagePath.CONFIRM);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR,"Couldnt confirm acc for user" ,e );
        }
        router = new Router(page,Router.RouteType.REDIRECT);
        return router;
    }
}
