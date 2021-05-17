package com.example.WEB_App.controller.command.impl;

import com.example.WEB_App.controller.command.Command;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class ToCreateTopPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page ;
        CustomUser user = (CustomUser) request.getSession().getAttribute("user");
        if(user != null) {
            page = ConfigurationManager.getProperty("path.page.create_top");
        } else {
            page = ConfigurationManager.getProperty("path.index");
        }
        return page;
    }
}
