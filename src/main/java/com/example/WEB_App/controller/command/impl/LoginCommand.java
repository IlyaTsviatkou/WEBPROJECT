package com.example.WEB_App.controller.command.impl;

import com.example.WEB_App.controller.command.Command;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.resource.ConfigurationManager;
import com.example.WEB_App.resource.MessageManager;
import com.example.WEB_App.model.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String ATTRIBUTE_NAME_USER = "user";
    private static final String ATTRIBUTE_NAME_ERROR_LOGIN = "errorLogin";
    LoginService loginService;

    public LoginCommand(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        Optional<CustomUser> user = Optional.empty();

        user = loginService.login(login, pass);
        if (user.isPresent()) {
            request.setAttribute(ATTRIBUTE_NAME_USER, user.get());
            request.getSession().setAttribute("user", user.get());
            page = ConfigurationManager.getProperty("path.page.profile");
        } else {
            request.setAttribute(ATTRIBUTE_NAME_ERROR_LOGIN, MessageManager.getProperty("message.errorlogin"));
            page = ConfigurationManager.getProperty("path.index");
        }
        return page;
    }
}
