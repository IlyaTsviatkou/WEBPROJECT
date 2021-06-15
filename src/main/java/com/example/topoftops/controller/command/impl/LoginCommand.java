package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.entity.CustomUser;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.resource.ConfigurationManager;
import com.example.topoftops.resource.MessageManager;
import com.example.topoftops.model.service.LoginService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_LOGIN;
import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_PASSWORD;

public class LoginCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_NAME_USER = "user";
    private static final String ATTRIBUTE_NAME_ERROR_LOGIN = "errorLogin";
    private LoginService loginService;

    public LoginCommand(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        Optional<CustomUser> user;
        try {
            user = loginService.login(login, pass);
            if (user.isPresent()) {
                request.setAttribute(ATTRIBUTE_NAME_USER, user.get());
                request.getSession().setAttribute(ATTRIBUTE_NAME_USER, user.get());
                page = ConfigurationManager.getProperty(PagePath.PROFILE);
            } else {
                request.setAttribute(ATTRIBUTE_NAME_ERROR_LOGIN, MessageManager.getProperty("message.errorlogin"));
                page = ConfigurationManager.getProperty(PagePath.INDEX);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR,e);
            page = ConfigurationManager.getProperty(PagePath.INDEX);
        }
        router = new Router(page, Router.RouteType.REDIRECT);
        return router;
    }
}