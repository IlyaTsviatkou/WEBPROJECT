package com.example.WEB_App.controller.command.impl;

import com.example.WEB_App.controller.command.Command;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.entity.Top;
import com.example.WEB_App.model.service.CreateTopService;
import com.example.WEB_App.resource.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CreateTopCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String PARAM_NAME_TITLE= "title";
    private static final String PARAM_NAME_DESCRIPTION = "description";
    private static final String PARAM_NAME_IMAGE = "image";
    CreateTopService service;

    public CreateTopCommand(CreateTopService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        CustomUser user = (CustomUser) request.getSession().getAttribute("user");
        String title = request.getParameter(PARAM_NAME_TITLE);
        String description = request.getParameter(PARAM_NAME_DESCRIPTION);
        String image = request.getParameter(PARAM_NAME_IMAGE);//todo add image saving
        Top top= new Top(title,description,image,user.getId());//fixme mby do it in service
        boolean result = false;
        result = service.create(top);
        if (result) {
            request.setAttribute("user", user);//fixme use mby session or smth
            page = ConfigurationManager.getProperty("path.page.profile");
        } else {
           // request.setAttribute(ATTRIBUTE_NAME_ERROR_LOGIN, MessageManager.getProperty("message.errorlogin"));
            page = ConfigurationManager.getProperty("path.index");
        }
        return page;
    }
}
