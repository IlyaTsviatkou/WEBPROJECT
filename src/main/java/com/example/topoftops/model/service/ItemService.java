package com.example.topoftops.model.service;

import com.example.topoftops.entity.Item;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.dao.impl.ItemDaoImpl;
import com.example.topoftops.validation.InputInfoValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;
import java.util.Optional;

public class ItemService {
    private static final Logger logger = LogManager.getLogger();
    private ItemDaoImpl dao = new ItemDaoImpl();

    /**
     * find items by top id
     * @param id
     * @return
     * @throws ServiceException
     */
    public ArrayList<Item> findItems(long id) throws ServiceException {
        ArrayList<Item> items;
        try {
            items = dao.findAllItems(id);
        } catch (Exception e) {
            logger.log(Level.WARN, e);
            throw new ServiceException(e);
        }
        return items;
    }

    /**
     * Create item
     * @param item
     * @throws ServiceException
     */
    public void create(Item item) throws ServiceException {
        try {
            int place = dao.findMaxPlace(item.getTop());
            item.setPlace(place + 1);
            if (InputInfoValidator.isValidTitle(item.getTitle()) && InputInfoValidator.isValidDescription(item.getDescription())) {
                dao.create(item);
            } else {
                logger.log(Level.WARN, "invalid input");
                throw new ServiceException("invalid input");
            }
        } catch (Exception e) {
            logger.log(Level.WARN, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Delete item by id
     * @param id
     * @throws ServiceException
     */
    public void delete(long id) throws ServiceException {
        try {
            Item item = dao.findItemById(id).get();
            dao.updateItemsAboveGiven(item.getTop(), item.getPlace());
            dao.delete(id);
        } catch (Exception e) {
            logger.log(Level.WARN, e);
            throw new ServiceException(e);
        }
    }

    /**
     * delete Item by top id
     * @param top
     * @throws ServiceException
     */
    public void deleteByTop(long top) throws ServiceException {
        try {
            dao.deleteByTop(top);
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
            throw new ServiceException(e);
        }
    }

    /**
     * update item
     * @param item
     * @throws ServiceException
     */
    public void update(Item item) throws ServiceException {
        try {
            if (InputInfoValidator.isValidTitle(item.getTitle()) && InputInfoValidator.isValidDescription(item.getDescription())) {
                dao.update(item);
            } else {
                logger.log(Level.WARN, "invalid input");
                throw new ServiceException("invalid input");
            }
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
            throw new ServiceException(e);
        }
    }

    /**
     * cahnge place of item by 1 or -1
     * @param id
     * @param count
     * @throws ServiceException
     */
    public void changePlace(long id, int count) throws ServiceException {
        try {
            Optional<Item> itemOptional = dao.findItemById(id);
            if (itemOptional.isPresent()) {
                Item item = itemOptional.get();
                int newPlace = item.getPlace() + count;
                itemOptional = dao.findItemByTopPlace(item.getTop(), newPlace);
                if (itemOptional.isPresent()) {
                    Item item2 = itemOptional.get();
                    dao.changePlace(item2.getId(), item.getPlace());
                    dao.changePlace(id, newPlace);
                }
            }
        } catch (DaoException e) {
            logger.log(Level.WARN,e);
            throw new ServiceException(e);
        }
    }

    /**
     * find item by id
     * @param id
     * @return
     * @throws ServiceException
     */
    public Item findItemById(long id) throws ServiceException {
        Item item;
        try {
            Optional<Item> itemOptional = dao.findItemById(id);
            if (itemOptional.isPresent()) {
                item = itemOptional.get();
            } else {
                logger.log(Level.WARN, "NO SUCH ITEM");
                throw new ServiceException("NO SUCH ITEM");
            }
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
            throw new ServiceException(e);
        }
        return item;
    }
}
