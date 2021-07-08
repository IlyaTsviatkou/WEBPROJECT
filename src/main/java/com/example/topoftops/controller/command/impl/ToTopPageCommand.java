package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.*;
import com.example.topoftops.entity.Item;
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

import static com.example.topoftops.controller.command.RequestParam.PARAM_ERROR_MESSAGE;
import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_TOP;

/**
 * The command is responsible for going to top page
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class ToTopPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_TOP = "top";
    private ItemService itemService;
    private TopService topService;

    public ToTopPageCommand(ItemService itemService, TopService topService) {
        this.itemService = itemService;
        this.topService = topService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page = ConfigurationManager.getProperty(PagePath.ERROR);
        Optional<Top> optionalTop;
        try {
            long topId = Long.parseLong(request.getParameter(PARAM_NAME_TOP));
            optionalTop = topService.findById(topId);
            Top top;
            if (optionalTop.isPresent()) {
                ArrayList<Item> items;
                top = (Top) optionalTop.get();
                items = itemService.findItems(top.getId());
                top.setItems(items);
                request.setAttribute(ATTRIBUTE_TOP, top);
                page = ConfigurationManager.getProperty(PagePath.TOP);
            } else {
                request.getSession().setAttribute(PARAM_ERROR_MESSAGE, "any problem with top");
            }
        } catch (ServiceException e) {
            request.getSession().setAttribute(PARAM_ERROR_MESSAGE, "any problem with top");
            logger.log(Level.WARN, e);
        }
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
