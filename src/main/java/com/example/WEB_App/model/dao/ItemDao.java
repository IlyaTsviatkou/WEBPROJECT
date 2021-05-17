package com.example.WEB_App.model.dao;

import com.example.WEB_App.entity.Item;
import com.example.WEB_App.entity.Top;
import com.example.WEB_App.exception.DaoException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDao {
    void create(Item item) throws DaoException, SQLException;
    void delete(long id) throws DaoException, SQLException;
    ArrayList<Item> findAllItems(long id) throws DaoException, SQLException;
    Item findItemByTitle(String title) throws DaoException, SQLException;
}
