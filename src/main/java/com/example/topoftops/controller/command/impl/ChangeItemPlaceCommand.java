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
import java.util.Optional;

import static com.example.topoftops.controller.command.RequestParam.*;

/**
 * The command is responsible for changing place of item
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class ChangeItemPlaceCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_TOP = "top";
    private ItemService itemService;
    private TopService topService;

    public ChangeItemPlaceCommand(ItemService itemService, TopService topService) {
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
            int count = Integer.parseInt(request.getParameter(PARAM_NAME_COUNT));
            long topId = Long.parseLong(request.getParameter(PARAM_NAME_TOP));
            User user = (User) request.getSession().getAttribute("user");
            itemService.changePlace(itemId, count);
            optionalTop = topService.findById(topId);
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
                    request.getSession().setAttribute(PARAM_ERROR_MESSAGE, "user tried change item to stranger top");
                    page = ConfigurationManager.getProperty(PagePath.ERROR);
                }
            } else {
                request.getSession().setAttribute(PARAM_ERROR_MESSAGE, "any problem with item additing");
                page = ConfigurationManager.getProperty(PagePath.ERROR);
            }
        } catch (ServiceException e) {
            request.getSession().setAttribute(PARAM_ERROR_MESSAGE, "any problem with item additing");
            page = ConfigurationManager.getProperty(PagePath.ERROR);
            logger.log(Level.WARN, e);
        }
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
