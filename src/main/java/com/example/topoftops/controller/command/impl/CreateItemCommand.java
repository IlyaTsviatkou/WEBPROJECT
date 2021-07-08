package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.*;
import com.example.topoftops.entity.User;
import com.example.topoftops.entity.Item;
import com.example.topoftops.entity.Role;
import com.example.topoftops.entity.Top;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.ItemService;
import com.example.topoftops.model.service.TopService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static com.example.topoftops.controller.command.RequestParam.*;

/**
 * The command is responsible for creating item
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class CreateItemCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_TOP = "top";
    private ItemService itemService;
    private TopService topService;

    public CreateItemCommand(ItemService itemService, TopService topService) {
        this.itemService = itemService;
        this.topService = topService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page;
        long id = Long.parseLong(request.getParameter(PARAM_NAME_TOP));
        User user = (User) request.getSession().getAttribute("user");
        Top top = null;
        try {
            top = (Top) topService.findById(id).get();
            if (user.getId() == top.getUser() || user.getRole() == Role.ADMIN.ordinal()) {
                String title = request.getParameter(PARAM_NAME_TITLE);
                String description = request.getParameter(PARAM_NAME_DESCRIPTION);
                String image = (String) request.getAttribute(PARAM_NAME_IMAGE_NAME);
                Item item = new Item(title, description, image);
                item.setTop(id);
                itemService.create(item);
                ArrayList<Item> items = itemService.findItems(top.getId());
                top.setItems(items);
                page = ConfigurationManager.getProperty(PagePath.TOP);
                router = new Router(page, Router.RouteType.FORWARD);
            } else {
                logger.log(Level.ERROR, "any problem with creating item");
                request.getSession().setAttribute(PARAM_ERROR_MESSAGE, "any problem with creating item");
                page = ConfigurationManager.getProperty(PagePath.ERROR);
                router = new Router(page, Router.RouteType.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.getSession().setAttribute(PARAM_ERROR_MESSAGE, "any problem with creating item");
            page = ConfigurationManager.getProperty(PagePath.ERROR);
            router = new Router(page, Router.RouteType.REDIRECT);
        }
        request.setAttribute(ATTRIBUTE_TOP, top);

        return router;
    }
}
