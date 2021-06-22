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
    public static final Logger logger = LogManager.getLogger();
    private static final String SQL_INSERT_CREATE = "INSERT INTO " + ITEM_TABLE + "(" +
             COLUMN_TITLE + "," +  COLUMN_DESCRIPTION + "," +
             COLUMN_TOP + "," +  COLUMN_IMAGE + ")" +
            "VALUES(?,?,?,?)";
    private static final String SQL_SELECT_ALL_ITEMS_BY_TOPID = "SELECT " +  COLUMN_ID + ", " +
             COLUMN_TITLE + ", " +
             COLUMN_DESCRIPTION + ", " +
             COLUMN_TOP + ", " +
             COLUMN_IMAGE + " FROM " +
             ITEM_TABLE + " WHERE " +
             COLUMN_TOP + " like ?";
    private static final String SQL_SELECT_BY_TITLE = "SELECT " +  COLUMN_ID + ", " +
             COLUMN_TITLE + ", " +
             COLUMN_IMAGE + ", " +
             COLUMN_TOP + ", " +
             COLUMN_DESCRIPTION + " FROM " +
             ITEM_TABLE + " WHERE " +
             COLUMN_TITLE + " like ?";
    private static final String SQL_DELETE_BY_ID ="DELETE FROM " +  ITEM_TABLE +
            " where " +  COLUMN_ID + " =?";
    private static final String SQL_DELETE_BY_TOP_ID ="DELETE FROM " +  ITEM_TABLE +
            " where " +  COLUMN_TOP + " =?";
    private static final String SQL_UPDATE_BY_ID ="UPDATE " +  ITEM_TABLE + " SET " +
             COLUMN_TITLE + " =?," +
             COLUMN_DESCRIPTION + " =?," +
             COLUMN_IMAGE + " =?," +
            " WHERE " +  COLUMN_ID + " =?";


    @Override
    public void create(Item item) throws DaoException {

        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_CREATE)){
            preparedStatement.setString(1, item.getTitle());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setLong(3, item.getTop());
            preparedStatement.setString(4, item.getImage());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARN,e.getMessage());
            throw new DaoException(e);
        }

    }

    public void delete(long id) throws DaoException{
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement= connection.prepareStatement(SQL_DELETE_BY_ID)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
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
                item.setTitle(resultSet.getString( COLUMN_TITLE));
                item.setDescription(resultSet.getString( COLUMN_DESCRIPTION));
                item.setImage(resultSet.getString( COLUMN_IMAGE));
                item.setTop(resultSet.getLong( COLUMN_TOP));
                item.setId(resultSet.getLong( COLUMN_ID));
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
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_TITLE)) {
            statement.setString(1, title );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                item.setTitle(resultSet.getString( COLUMN_TITLE));
                item.setDescription(resultSet.getString( COLUMN_DESCRIPTION));
                item.setImage(resultSet.getString( COLUMN_IMAGE));
                item.setTop(resultSet.getLong( COLUMN_TOP));
                item.setId(resultSet.getLong( COLUMN_ID));
            }
        } catch (SQLException e) {
            logger.log(Level.WARN,e.getMessage());
            throw new DaoException(e);
        }
        return item;
    }
}
