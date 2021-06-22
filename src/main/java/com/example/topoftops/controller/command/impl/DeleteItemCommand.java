package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.entity.Item;
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
        Top top = null;
        try {
            top = (Top) topService.findById(topId).get();
            itemService.delete(itemId);
            ArrayList<Item> items = itemService.find(topId);//fixme idk how to do it right
            top.setItems(items);
        } catch (ServiceException e) {
            logger.log(Level.ERROR,e);
        }
        request.setAttribute(ATTRIBUTE_TOP,top);
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
