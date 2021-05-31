package com.example.WEB_App.controller.command.impl;

import com.example.WEB_App.controller.command.Command;
import com.example.WEB_App.controller.command.Router;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.model.service.UserService;
import com.example.WEB_App.resource.ConfigurationManager;
import com.example.WEB_App.resource.MessageManager;
import com.example.WEB_App.model.service.RegisterService;
import com.example.WEB_App.util.Encryptor;
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
    UserService userService;

    public RegisterCommand(UserService service) {
        this.userService = service;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page = null;
        long id = (long) new Random().nextInt(99999) + 1;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        CustomUser user= new CustomUser(id,login,pass,email,2,0);
        boolean result = false;
        try {
            result = userService.register(user);
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARN,e.getMessage());
        }
//fixme check if user is exist
        if (result) {
            request.setAttribute(ATTRIBUTE_NAME_USER, user);
            String ecryptedStr = Encryptor.encryptC(String.valueOf(user.getId()));
            String link = "http://localhost:8080/WEB_App_war_exploded/controller?command=confirm_registration&userId="+ecryptedStr;
            String message = "To confirm ur account use THIS: " + link;
            userService.sendMessage(user.getEmail(),message);
            page = ConfigurationManager.getProperty("path.page.profile");
        } else {
            request.setAttribute(ATTRIBUTE_NAME_ERROR_LOGIN, MessageManager.getProperty("message.errorlogin"));
            page = ConfigurationManager.getProperty("path.index");
        }
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
