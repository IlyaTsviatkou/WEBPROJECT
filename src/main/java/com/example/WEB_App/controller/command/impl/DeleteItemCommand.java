package com.example.WEB_App.controller.command.impl;

import com.example.WEB_App.controller.command.Command;
import com.example.WEB_App.entity.Item;
import com.example.WEB_App.entity.Top;
import com.example.WEB_App.model.service.ItemService;
import com.example.WEB_App.model.service.TopService;
import com.example.WEB_App.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class DeleteItemCommand implements Command {
    private static final String PARAM_NAME_TOP = "topid";
    private static final String PARAM_NAME_ITEM = "itemid";
    private static final String PARAM_NAME_TITLE = "title";
    private static final String PARAM_NAME_DESCRIPTION = "description";
    private static final String PARAM_NAME_IMAGE = "image";
    ItemService itemService;
    TopService topService;

    public DeleteItemCommand(ItemService itemService, TopService topService) {
        this.itemService = itemService;
        this.topService = topService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.top");
        long topId = Long.parseLong(request.getParameter(PARAM_NAME_TOP));
        long itemId = Long.parseLong(request.getParameter(PARAM_NAME_ITEM));
        Top top = (Top) topService.findById(topId).get();
        itemService.delete(itemId);
        ArrayList<Item> items = itemService.find(topId);//fixme idk how to do it right
        top.setItems(items);
        request.setAttribute("top",top);
        return page;
    }
}
