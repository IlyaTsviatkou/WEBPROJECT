package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.ConfigurationManager;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.model.service.AdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ToAdminPanelPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private AdminService adminService;
    public ToAdminPanelPageCommand(AdminService adminService) {
        this.adminService = adminService;
    }
    @Override
    public Router execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(PagePath.ADMIN_PANEL);
        Router router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
