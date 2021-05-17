package com.example.WEB_App.controller.command.impl;

import com.example.WEB_App.controller.command.Command;
import com.example.WEB_App.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class ToRegisterPageCommand implements Command {
    public ToRegisterPageCommand() {
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.register");
        return page;
    }
}
