package com.example.WEB_App.controller.command.impl;

import com.example.WEB_App.controller.command.Command;
import com.example.WEB_App.controller.command.Router;
import com.example.WEB_App.entity.Item;
import com.example.WEB_App.entity.Top;
import com.example.WEB_App.model.service.ItemService;
import com.example.WEB_App.model.service.TopService;
import com.example.WEB_App.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

public class ToTopPageCommand implements Command {
    private static final String PARAM_NAME_TITLE = "title";
    ItemService itemService;
    TopService topService;
    public  ToTopPageCommand(ItemService itemService, TopService topService) {
        this.itemService = itemService;
        this.topService = topService;
    }
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page;
        Optional<Top> optionalTop = topService.findByTitle(request.getParameter(PARAM_NAME_TITLE));
        Top top;
        if(optionalTop.isPresent()) {
            ArrayList<Item> items = new ArrayList<>();
            top = (Top) optionalTop.get();
            items = itemService.find(top.getId());
            top.setItems(items);
            request.setAttribute("top",top);
            page = ConfigurationManager.getProperty("path.page.top");
        } else {
            page = ConfigurationManager.getProperty("path.index");
        }
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
