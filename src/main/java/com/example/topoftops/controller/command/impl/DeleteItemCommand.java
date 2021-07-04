package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.entity.User;
import com.example.topoftops.entity.Item;
import com.example.topoftops.entity.Role;
import com.example.topoftops.entity.Top;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.ItemService;
import com.example.topoftops.model.service.TopService;
import com.example.topoftops.controller.command.ConfigurationManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static com.example.topoftops.controller.command.RequestParam.*;

/**
 * The command is responsible for delete item
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class DeleteItemCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_TOP = "top";
    private ItemService itemService;
    private TopService topService;

    public DeleteItemCommand(ItemService itemService, TopService topService) {
        this.itemService = itemService;
        this.topService = topService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page = ConfigurationManager.getProperty(PagePath.TOP);
        long topId = Long.parseLong(request.getParameter(PARAM_NAME_TOP));
        long itemId = Long.parseLong(request.getParameter(PARAM_NAME_ITEM));
        User user = (User) request.getSession().getAttribute("user");
        Top top = null;
        try {
            top = (Top) topService.findById(topId).get();
            if (user.getId() == top.getUser() || user.getRole() == Role.ADMIN.ordinal()) {
                itemService.delete(itemId);
                ArrayList<Item> items = itemService.findItems(topId);
                top.setItems(items);
            } else {
                logger.log(Level.ERROR, "user tried delete item to stranger top");
                page = ConfigurationManager.getProperty(PagePath.INDEX);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            page = ConfigurationManager.getProperty(PagePath.INDEX);
        }
        request.setAttribute(ATTRIBUTE_TOP, top);
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
