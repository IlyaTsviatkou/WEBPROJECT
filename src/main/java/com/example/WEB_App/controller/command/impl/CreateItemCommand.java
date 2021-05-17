package com.example.WEB_App.controller.command.impl;

import com.example.WEB_App.controller.command.Command;
import com.example.WEB_App.entity.Item;
import com.example.WEB_App.entity.Top;
import com.example.WEB_App.model.service.ItemService;
import com.example.WEB_App.model.service.TopService;
import com.example.WEB_App.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class CreateItemCommand implements Command {
    private static final String PARAM_NAME_TOP = "topid";
    private static final String PARAM_NAME_TITLE = "title";
    private static final String PARAM_NAME_DESCRIPTION = "description";
    private static final String PARAM_NAME_IMAGE = "image";
    ItemService itemService;
    TopService topService;

    public CreateItemCommand(ItemService itemService, TopService topService) {
        this.itemService = itemService;
        this.topService = topService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.top");//fixme
        long id = Long.parseLong(request.getParameter(PARAM_NAME_TOP));
        Top top = (Top) topService.findById(id).get();
        String title = request.getParameter(PARAM_NAME_TITLE);
        String description = request.getParameter(PARAM_NAME_DESCRIPTION);
        String image = request.getParameter(PARAM_NAME_IMAGE);
        Item item = new Item(title, description, image);
        item.setTop(id);
        itemService.create(item);
        ArrayList<Item> items = itemService.find(top.getId());//fixme idk how to do it right
        top.setItems(items);
        request.setAttribute("top",top);
        return page;
    }
}
