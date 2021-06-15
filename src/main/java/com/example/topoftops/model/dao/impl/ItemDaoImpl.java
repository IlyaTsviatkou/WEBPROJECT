package com.example.topoftops.model.dao.impl;

import com.example.topoftops.entity.Item;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.dao.ColumnName;
import com.example.topoftops.model.dao.ItemDao;
import com.example.topoftops.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

import static com.example.topoftops.model.dao.ColumnName.*;

public class ItemDaoImpl implements ItemDao {
    public static Logger logger = LogManager.getLogger();
    private static final String SQL_INSERT_CREATE = "INSERT INTO " + ITEM_TABLE + "(" +
             COLUMN_ITEM_TITLE + "," +  COLUMN_ITEM_DESCRIPTION + "," +
             COLUMN_ITEM_TOP + "," +  COLUMN_ITEM_IMAGE + ")" +
            "VALUES(?,?,?,?)";
    private static final String SQL_SELECT_ALL_ITEMS_BY_TOPID = "SELECT " +  COLUMN_ITEM_ID + ", " +
             COLUMN_ITEM_TITLE + ", " +
             COLUMN_ITEM_DESCRIPTION + ", " +
             COLUMN_ITEM_TOP + ", " +
             COLUMN_ITEM_IMAGE + " FROM " +
             ITEM_TABLE + " WHERE " +
             COLUMN_ITEM_TOP + " like ?";
    private static final String SQL_SELECT_ITEM_BY_TITLE = "SELECT " +  COLUMN_ITEM_ID + ", " +
             COLUMN_ITEM_TITLE + ", " +
             COLUMN_ITEM_IMAGE + ", " +
             COLUMN_ITEM_TOP + ", " +
             COLUMN_ITEM_DESCRIPTION + " FROM " +
             ITEM_TABLE + " WHERE " +
             COLUMN_ITEM_TITLE + " like ?";
    private static final String SQL_DELETE_ITEM_BY_ID ="DELETE FROM " +  ITEM_TABLE +
            " where " +  COLUMN_ITEM_ID + " =?";
    private static final String SQL_DELETE_ITEM_BY_TOP_ID ="DELETE FROM " +  ITEM_TABLE +
            " where " +  COLUMN_ITEM_TOP + " =?";
    private static final String SQL_UPDATE_ITEM_BY_ID ="UPDATE " +  ITEM_TABLE + " SET " +
             COLUMN_ITEM_TITLE + " =?," +
             COLUMN_ITEM_DESCRIPTION + " =?," +
             COLUMN_ITEM_IMAGE + " =?," +
            " WHERE " +  COLUMN_ITEM_ID + " =?";


    @Override
    public void create(Item item) throws DaoException {

        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement prSt = connection.prepareStatement(SQL_INSERT_CREATE)){
            prSt.setString(1, item.getTitle());
            prSt.setString(2, item.getDescription());
            prSt.setLong(3, item.getTop());
            prSt.setString(4, item.getImage());
            prSt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARN,e.getMessage());
            throw new DaoException(e);
        }

    }

    public void delete(long id) throws DaoException{
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement prSt= connection.prepareStatement(SQL_DELETE_ITEM_BY_ID)){
            prSt.setLong(1, id);
            prSt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARN,e.getMessage());
            throw new DaoException(e);
        }

    }

    @Override
    public ArrayList<Item> findAllItems(long id) throws DaoException {
        ArrayList<Item> items = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ITEMS_BY_TOPID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Item item = new Item();
                item.setTitle(resultSet.getString( COLUMN_ITEM_TITLE));
                item.setDescription(resultSet.getString( COLUMN_ITEM_DESCRIPTION));
                item.setImage(resultSet.getString( COLUMN_ITEM_IMAGE));
                item.setTop(resultSet.getLong( COLUMN_ITEM_TOP));
                item.setId(resultSet.getLong( COLUMN_ITEM_ID));
                items.add(item);
            }
        } catch (SQLException e) {
            logger.log(Level.WARN,e.getMessage());
            throw new DaoException(e);
        }
        return items;
    }

    @Override
    public Item findItemByTitle(String title) throws DaoException {
       Item item = new Item();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ITEM_BY_TITLE)) {
            statement.setString(1, title );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                item.setTitle(resultSet.getString( COLUMN_ITEM_TITLE));
                item.setDescription(resultSet.getString( COLUMN_ITEM_DESCRIPTION));
                item.setImage(resultSet.getString( COLUMN_ITEM_IMAGE));
                item.setTop(resultSet.getLong( COLUMN_ITEM_TOP));
                item.setId(resultSet.getLong( COLUMN_ITEM_ID));
            }
        } catch (SQLException e) {
            logger.log(Level.WARN,e.getMessage());
            throw new DaoException(e);
        }
        return item;
    }
}
