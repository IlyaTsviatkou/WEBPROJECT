package com.example.WEB_App.controller.command.impl;

import com.example.WEB_App.controller.command.Command;
import com.example.WEB_App.resource.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class NoCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    @Override
    public String execute(HttpServletRequest request) {
            String page = ConfigurationManager.getProperty("path.index");
            return page;
    }
}
