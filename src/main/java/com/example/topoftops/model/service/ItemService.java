package com.example.topoftops.model.service;

import com.example.topoftops.entity.Item;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.dao.impl.ItemDaoImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;

public class ItemService {
    private static Logger logger = LogManager.getLogger();
    private ItemDaoImpl dao = new ItemDaoImpl();

    public ArrayList<Item> find(long id) throws ServiceException {
        ArrayList<Item> items = new ArrayList<>();
        try {
            items = dao.findAllItems(id);
        } catch (Exception e) {
            logger.log(Level.WARN,e.getMessage());
            throw new ServiceException(e);
        }
        return items;
    }

    public void create(Item item) throws ServiceException {
        try {
            dao.create(item);
        } catch (Exception e) {
            logger.log(Level.WARN,e.getMessage());
            throw new ServiceException(e);
        }
    }

    public void delete(long id) throws ServiceException {
        try {
            dao.delete(id);
        } catch (Exception e) {
            logger.log(Level.WARN,e.getMessage());
            throw new ServiceException(e);
        }
    }
}
