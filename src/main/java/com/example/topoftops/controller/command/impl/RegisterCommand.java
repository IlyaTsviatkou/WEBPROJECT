package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.entity.CustomUser;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.UserService;
import com.example.topoftops.controller.command.ConfigurationManager;
import com.example.topoftops.controller.command.MessageManager;
import com.example.topoftops.util.Encryptor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.topoftops.controller.command.RequestParam.*;

public class RegisterCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_NAME_USER = "user";
    private static final String ATTRIBUTE_NAME_ERROR_LOGIN = "errorLogin";
    private static final int ROLE_USER = 2;
    private static final int STATUS_ACTIVE = 1;
    private UserService userService;

    public RegisterCommand(UserService service) {
        this.userService = service;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        CustomUser user = new CustomUser(login, pass, email, ROLE_USER, STATUS_ACTIVE);
        boolean result = false;
        try {
            result = userService.register(user);

            //fixme check if user is exist
            if (result) {
                request.setAttribute(ATTRIBUTE_NAME_USER, user);

                String ecryptedStr = Encryptor.encryptC(user.getLogin());
                String link = "http://localhost:8080/WEB_App_war_exploded/controller?command=confirm_registration&userId=" + ecryptedStr;
                String message = "To confirm ur account use THIS: " + link;
                boolean isSent = userService.sendMessage(user.getEmail(), message);
                if(!isSent) {
                    logger.log(Level.WARN,"email is not valid" + user.getEmail());
                }
                page = ConfigurationManager.getProperty(PagePath.PROFILE);
            } else {
                request.setAttribute(ATTRIBUTE_NAME_ERROR_LOGIN, MessageManager.getProperty("message.errorlogin"));
                page = ConfigurationManager.getProperty(PagePath.INDEX);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR,e);
            page = ConfigurationManager.getProperty(PagePath.INDEX);
        }
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
