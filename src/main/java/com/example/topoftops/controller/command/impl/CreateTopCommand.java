package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.entity.CustomUser;
import com.example.topoftops.entity.Top;
import com.example.topoftops.model.service.CreateTopService;
import com.example.topoftops.controller.command.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import static com.example.topoftops.controller.command.RequestParam.*;

public class CreateTopCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_USER = "user";
    private CreateTopService service;

    public CreateTopCommand(CreateTopService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page;
        CustomUser user = (CustomUser) request.getSession().getAttribute(ATTRIBUTE_USER);
        String title = request.getParameter(PARAM_NAME_TITLE);
        String description = request.getParameter(PARAM_NAME_DESCRIPTION);
        String image = request.getAttribute(PARAM_NAME_IMAGE_NAME).toString();//todo add image saving
        Top top= new Top(title,description,image,user.getId());//fixme mby do it in service
        boolean result;
        result = service.create(top);
        if (result) {
            request.setAttribute(ATTRIBUTE_USER, user);//fixme use mby session or smth
            page = ConfigurationManager.getProperty(PagePath.PROFILE);
        } else {
           // request.setAttribute(ATTRIBUTE_NAME_ERROR_LOGIN, MessageManager.getProperty("message.errorlogin"));
            page = ConfigurationManager.getProperty(PagePath.INDEX);
        }
        router = new Router(page, Router.RouteType.REDIRECT);
        return router;
    }
}
