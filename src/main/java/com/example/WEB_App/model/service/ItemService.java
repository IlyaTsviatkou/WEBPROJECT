package com.example.WEB_App.model.service;

import com.example.WEB_App.entity.Item;
import com.example.WEB_App.entity.Top;
import com.example.WEB_App.model.dao.impl.ItemDaoImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;

public class ItemService {
    public static Logger logger = LogManager.getLogger();
    ItemDaoImpl dao = new ItemDaoImpl();

    public ArrayList<Item> find(long id) {
        ArrayList<Item> items = new ArrayList<>();
        try {
            items = dao.findAllItems(id);
        } catch (Exception e) {
            logger.log(Level.WARN,e.getMessage());
        }
        return items;
    }
    public boolean create(Item item) {
        boolean result = true;
        try {
            dao.create(item);
        } catch (Exception e) {
            logger.log(Level.WARN,e.getMessage());
            result = false;
        }

        return result;
    }

    public boolean delete(long id) {
        boolean result = true;
        try {
            dao.delete(id);
        } catch (Exception e) {
            logger.log(Level.WARN,e.getMessage());
            result = false;
        }

        return result;
    }
}
