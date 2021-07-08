package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.*;
import com.example.topoftops.entity.User;
import com.example.topoftops.entity.Top;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.ItemService;
import com.example.topoftops.model.service.TopService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.topoftops.controller.command.RequestParam.*;

/**
 * The command is responsible for creating top
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class CreateTopCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_TOP = "top";
    private TopService topService;
    private ItemService itemService;

    public CreateTopCommand(TopService topService, ItemService itemService) {
        this.topService = topService;
        this.itemService = itemService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page;
        User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        String title = request.getParameter(PARAM_NAME_TITLE);
        String description = request.getParameter(PARAM_NAME_DESCRIPTION);
        String image = (String) request.getAttribute(PARAM_NAME_IMAGE_NAME);
        Top top = new Top(title, description, image, user.getId());
        page = ConfigurationManager.getProperty(PagePath.ERROR);
        try {
            topService.create(top);
            page = ConfigurationManager.getProperty(PagePath.INDEX);
        } catch (ServiceException e) {
            logger.log(Level.WARN, e);
            request.getSession().setAttribute(PARAM_ERROR_MESSAGE, "any problem with creating top");
        }
        router = new Router(page, Router.RouteType.REDIRECT);
        return router;
    }
}
