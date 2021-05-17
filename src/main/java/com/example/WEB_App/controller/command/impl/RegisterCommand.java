package com.example.WEB_App.controller.command.impl;

import com.example.WEB_App.controller.command.Command;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.resource.ConfigurationManager;
import com.example.WEB_App.resource.MessageManager;
import com.example.WEB_App.model.service.RegisterService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Random;

public class RegisterCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String ATTRIBUTE_NAME_USER = "user";
    private static final String ATTRIBUTE_NAME_ERROR_LOGIN = "errorLogin";
    RegisterService registerService;

    public RegisterCommand(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        long id = (long) new Random().nextInt(99999) + 1;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        CustomUser user= new CustomUser(id,login,pass,email,3);
        boolean result = false;
        try {
            result = registerService.register(user);
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARN,e.getMessage());
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
