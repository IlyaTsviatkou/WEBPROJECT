package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.entity.Item;
import com.example.topoftops.entity.Top;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.ItemService;
import com.example.topoftops.model.service.TopService;
import com.example.topoftops.resource.ConfigurationManager;
import com.example.topoftops.controller.command.RequestParam;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static com.example.topoftops.controller.command.RequestParam.*;


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
        Top top = null;
        try {
            top = (Top) topService.findById(id).get();
            String title = request.getParameter(PARAM_NAME_TITLE);
            String description = request.getParameter(PARAM_NAME_DESCRIPTION);
            String image = request.getParameter(PARAM_NAME_IMAGE);
            Item item = new Item(title, description, image);
            item.setTop(id);
            itemService.create(item);
            ArrayList<Item> items = itemService.find(top.getId());//fixme idk how to do it right
            top.setItems(items);
            page = ConfigurationManager.getProperty(PagePath.TOP);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            page = ConfigurationManager.getProperty(PagePath.INDEX);
        }
        request.setAttribute(ATTRIBUTE_TOP, top);
        router = new Router(page, Router.RouteType.FORWARD);//fixme should be redirect for def from F5 but doesnt work
        return router;
    }
}
