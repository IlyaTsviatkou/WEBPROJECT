package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.*;
import com.example.topoftops.entity.User;

import javax.servlet.http.HttpServletRequest;

import static com.example.topoftops.controller.command.RequestParam.PARAM_ERROR_MESSAGE;

/**
 * The command is responsible for going to create top page
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class ToCreateTopPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page;
        page = ConfigurationManager.getProperty(PagePath.CREATE_TOP);
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
