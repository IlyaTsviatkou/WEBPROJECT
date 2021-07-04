package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.ConfigurationManager;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
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
import java.util.Optional;

import static com.example.topoftops.controller.command.RequestParam.*;

/**
 * The command is responsible for UpdateItem
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class UpdateItemCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_TOP = "top";
    private ItemService itemService;
    private TopService topService;

    public UpdateItemCommand(ItemService itemService, TopService topService) {
        this.itemService = itemService;
        this.topService = topService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page;
        Optional<Top> optionalTop;
        try {
            long itemId = Long.parseLong(request.getParameter(PARAM_NAME_ITEM));
            String description = request.getParameter(PARAM_NAME_DESCRIPTION);
            String title = request.getParameter(PARAM_NAME_TITLE);
            User user = (User) request.getSession().getAttribute("user");
            Item item = itemService.findItemById(itemId);
            item.setTitle(title);
            item.setDescription(description);
            itemService.update(item);
            optionalTop = topService.findById(item.getTop());
            Top top;
            if (optionalTop.isPresent()) {
                ArrayList<Item> items;
                top = (Top) optionalTop.get();
                if (user.getId() == top.getUser() || user.getRole() == Role.ADMIN.ordinal()) {
                    items = itemService.findItems(top.getId());
                    top.setItems(items);
                    request.setAttribute(ATTRIBUTE_TOP, top);
                    page = ConfigurationManager.getProperty(PagePath.TOP);
                } else {
                    logger.log(Level.ERROR, "user tried change item to stranger top");
                    page = ConfigurationManager.getProperty(PagePath.INDEX);
                }
            } else {
                page = ConfigurationManager.getProperty(PagePath.INDEX);
            }
        } catch (ServiceException e) {
            page = ConfigurationManager.getProperty(PagePath.INDEX);
            logger.log(Level.WARN, e);
        }
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
