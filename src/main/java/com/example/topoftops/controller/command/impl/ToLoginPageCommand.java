package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.controller.command.ConfigurationManager;
import com.example.topoftops.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The command is responsible for login page
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class ToLoginPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_USER = "user";

    public ToLoginPageCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page;
        User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        if(user == null) {
            page = ConfigurationManager.getProperty(PagePath.LOGIN);
        } else {
            page = ConfigurationManager.getProperty(PagePath.INDEX);
            logger.log(Level.WARN,"tried login second time");
        }
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
