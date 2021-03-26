package com.example.WEB_App.command.impl;

import com.example.WEB_App.command.Command;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.resource.ConfigurationManager;
import com.example.WEB_App.resource.MessageManager;
import com.example.WEB_App.service.LoginService;
import com.example.WEB_App.service.RegisterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class RegisterCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_INFORMATION = "information";
    private static final String ATTRIBUTE_NAME_USER = "user";
    private static final String ATTRIBUTE_NAME_ERROR_LOGIN = "errorLogin";
    RegisterService registerService;

    public RegisterCommand(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        long id = (long) new Random().nextInt(1000) + 1000;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String information = request.getParameter(PARAM_NAME_INFORMATION);
        CustomUser user= new CustomUser(id,login,pass,information);
        boolean result = false;
        try {
            result = registerService.register(user);
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (result) {
            request.setAttribute(ATTRIBUTE_NAME_USER, user);
            page = ConfigurationManager.getProperty("path.page.profile");
        } else {
            request.setAttribute(ATTRIBUTE_NAME_ERROR_LOGIN, MessageManager.getProperty("message.errorlogin"));
            page = ConfigurationManager.getProperty("path.index");
        }
        return page;
    }
}
