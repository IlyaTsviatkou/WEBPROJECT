package com.example.topoftops.model.dao;

import com.example.topoftops.entity.Item;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.exception.ServiceException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * The interface for working with database table items
 *
 * @author Ilya Tsvetkov
 * @see BaseDao
 */
public interface ItemDao extends BaseDao<Item> {
    /**
     * Delete by top id
     *
     * @param id {@link Long} top id
     * @throws DaoException if {@link SQLException} occur
     */
    void deleteByTop(long id) throws DaoException;

    /**
     * Find all items from top
     *
     * @param id {@link Long} top id
     * @throws DaoException if {@link SQLException} occur
     */
    ArrayList<Item> findAllItems(long id) throws DaoException;

    /**
     * Find item by id
     *
     * @param id {@link Long} id
     * @throws DaoException if {@link SQLException} occur
     */
    Optional<Item> findItemById(long id) throws DaoException;

    /**
     * Find item by top and place
     *
     * @param top   {@link Long} top id
     * @param place {@link Integer} place
     * @throws DaoException if {@link SQLException} occur
     */
    Optional<Item> findItemByTopPlace(long top, int place) throws DaoException;

    /**
     * Update Item
     *
     * @param item {@link Item} item
     * @throws DaoException if {@link SQLException} occur
     */
    void update(Item item) throws DaoException;

    /**
     * Find max place form items with same topID
     *
     * @param topId {@link Long} top id
     * @return Max place
     * @throws DaoException if {@link SQLException} occur
     */
    int findMaxPlace(long topId) throws DaoException;

    /**
     * Change item with above given
     *
     * @param top   {@link Long} top id
     * @param place {@link Integer} new place
     * @throws DaoException if {@link SQLException} occur
     */
    void updateItemsAboveGiven(long top, int place) throws DaoException;

    /**
     * Change place
     *
     * @param itemId   {@link Long} item id
     * @param newPlace {@link Integer} new place
     * @throws DaoException if {@link SQLException} occur
     */
    void changePlace(long itemId, int newPlace) throws DaoException;
}
