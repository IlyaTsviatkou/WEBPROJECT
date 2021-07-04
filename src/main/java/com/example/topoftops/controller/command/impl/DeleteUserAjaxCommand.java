package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.AjaxCommand;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.topoftops.controller.command.RequestParam.*;

/**
 * The command is responsible for delete user
 *
 * @author Ilya Tsvetkov
 * @see AjaxCommand
 */
public class DeleteUserAjaxCommand implements AjaxCommand {
    private static final Logger logger = LogManager.getLogger();
    private UserService userService;

    public DeleteUserAjaxCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request) {
        try {
            String userid = request.getParameter(PARAM_NAME_USER);
            request.setAttribute("message", "test message");
            userService.delete(userid);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
    }
}
