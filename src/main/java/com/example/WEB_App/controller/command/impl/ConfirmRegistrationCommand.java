package com.example.WEB_App.controller.command.impl;

import com.example.WEB_App.controller.command.Command;
import com.example.WEB_App.controller.command.Router;
import com.example.WEB_App.exception.ServiceException;
import com.example.WEB_App.model.service.UserService;
import com.example.WEB_App.resource.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ConfirmRegistrationCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    UserService userService;
    public ConfirmRegistrationCommand(UserService userService) {
        this.userService = userService;
    }
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String userId = request.getParameter("userId");//fixme add message Error and etc.
        String page = ConfigurationManager.getProperty("path.index");;
        if (userService.activate(userId)) {
            page = ConfigurationManager.getProperty("path.page.confirm");
        }
        router = new Router(page,Router.RouteType.REDIRECT);
        return router;
    }
}
