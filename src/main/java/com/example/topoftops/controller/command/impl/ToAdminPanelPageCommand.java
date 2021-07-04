package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.ConfigurationManager;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The command is responsible for going to admin panel
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class ToAdminPanelPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    public ToAdminPanelPageCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(PagePath.ADMIN_PANEL);
        Router router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
