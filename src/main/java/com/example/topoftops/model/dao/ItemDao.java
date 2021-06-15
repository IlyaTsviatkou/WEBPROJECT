package com.example.topoftops.model.dao;

import com.example.topoftops.entity.Item;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.exception.ServiceException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDao {
    void create(Item item) throws DaoException;
    void delete(long id) throws DaoException;
    ArrayList<Item> findAllItems(long id) throws DaoException;
    Item findItemByTitle(String title) throws DaoException;
}
