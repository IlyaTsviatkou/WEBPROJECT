package com.example.WEB_App.model.dao.impl;

import com.example.WEB_App.entity.Item;
import com.example.WEB_App.entity.Top;
import com.example.WEB_App.exception.DaoException;
import com.example.WEB_App.model.dao.Const;
import com.example.WEB_App.model.dao.ItemDao;
import com.example.WEB_App.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class ItemDaoImpl implements ItemDao {
    public static Logger logger = LogManager.getLogger();
    private static final String SQL_INSERT_CREATE = "INSERT INTO " + Const.ITEM_TABLE + "(" +
            Const.COLUMN_ITEM_ID + "," +
            Const.COLUMN_ITEM_TITLE + "," + Const.COLUMN_ITEM_DESCRIPTION + "," +
            Const.COLUMN_ITEM_TOP + "," + Const.COLUMN_ITEM_IMAGE + ")" +
            "VALUES(?,?,?,?,?)";
    private static final String SQL_SELECT_ALL_ITEMS_BY_TOPID = "SELECT " + Const.COLUMN_ITEM_ID + ", " +
            Const.COLUMN_ITEM_TITLE + ", " +
            Const.COLUMN_ITEM_DESCRIPTION + ", " +
            Const.COLUMN_ITEM_TOP + ", " +
            Const.COLUMN_ITEM_IMAGE + " FROM " +
            Const.ITEM_TABLE + " WHERE " +
            Const.COLUMN_ITEM_TOP + " like ?";
    private static final String SQL_SELECT_ITEM_BY_TITLE = "SELECT " + Const.COLUMN_ITEM_ID + ", " +
            Const.COLUMN_ITEM_TITLE + ", " +
            Const.COLUMN_ITEM_IMAGE + ", " +
            Const.COLUMN_ITEM_TOP + ", " +
            Const.COLUMN_ITEM_DESCRIPTION + " FROM " +
            Const.ITEM_TABLE + " WHERE " +
            Const.COLUMN_ITEM_TITLE + " like ?";
    private static final String SQL_DELETE_ITEM_BY_ID ="DELETE FROM " + Const.ITEM_TABLE +
            " where " + Const.COLUMN_ITEM_ID + " =?";
    private static final String SQL_DELETE_ITEM_BY_TOP_ID ="DELETE FROM " + Const.ITEM_TABLE +
            " where " + Const.COLUMN_ITEM_TOP + " =?";
    private static final String SQL_UPDATE_ITEM_BY_ID ="UPDATE " + Const.ITEM_TABLE + " SET " +
            Const.COLUMN_ITEM_TITLE + " =?," +
            Const.COLUMN_ITEM_DESCRIPTION + " =?," +
            Const.COLUMN_ITEM_IMAGE + " =?," +
            " WHERE " + Const.COLUMN_ITEM_ID + " =?";


    @Override
    public void create(Item item) throws DaoException, SQLException {
        PreparedStatement prSt = null;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection()) {
            prSt = connection.prepareStatement(SQL_INSERT_CREATE);
            long id = (long) new Random().nextInt(99999) + 1;
            prSt.setLong(1, id);
            prSt.setString(2, item.getTitle());
            prSt.setString(3, item.getDescription());
            prSt.setLong(4, item.getTop());
            prSt.setString(5, item.getImage());
            prSt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARN,e.getMessage());
        } finally {
            if (prSt != null) {
                prSt.close();
            }
        }

    }

    public void delete(long id) throws DaoException, SQLException {
        PreparedStatement prSt = null;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection()) {
            prSt = connection.prepareStatement(SQL_DELETE_ITEM_BY_ID);
            prSt.setLong(1, id);
            prSt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARN,e.getMessage());
        } finally {
            if (prSt != null) {
                prSt.close();
            }
        }

    }

    @Override
    public ArrayList<Item> findAllItems(long id) throws DaoException {
        ArrayList<Item> items = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ITEMS_BY_TOPID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Item item = new Item();
                item.setTitle(resultSet.getString(Const.COLUMN_ITEM_TITLE));
                item.setDescription(resultSet.getString(Const.COLUMN_ITEM_DESCRIPTION));
                item.setImage(resultSet.getString(Const.COLUMN_ITEM_IMAGE));
                item.setTop(resultSet.getLong(Const.COLUMN_ITEM_TOP));
                item.setId(resultSet.getLong(Const.COLUMN_ITEM_ID));
                items.add(item);
            }
        } catch (SQLException e) {
            logger.log(Level.WARN,e.getMessage());
        }
        return items;
    }

    @Override
    public Item findItemByTitle(String title) throws DaoException {
       Item item = new Item();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ITEM_BY_TITLE);
            statement.setString(1, title );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                item.setTitle(resultSet.getString(Const.COLUMN_ITEM_TITLE));
                item.setDescription(resultSet.getString(Const.COLUMN_ITEM_DESCRIPTION));
                item.setImage(resultSet.getString(Const.COLUMN_ITEM_IMAGE));
                item.setTop(resultSet.getLong(Const.COLUMN_ITEM_TOP));
                item.setId(resultSet.getLong(Const.COLUMN_ITEM_ID));
            }
        } catch (SQLException e) {
            logger.log(Level.WARN,e.getMessage());
        }
        return item;
    }
}
