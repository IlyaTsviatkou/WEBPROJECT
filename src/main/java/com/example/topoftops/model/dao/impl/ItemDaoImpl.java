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
import java.util.Optional;

import static com.example.topoftops.model.dao.ColumnName.*;

public class ItemDaoImpl implements ItemDao {
    public static final Logger logger = LogManager.getLogger();
    private static final String SQL_INSERT_CREATE = "INSERT INTO " + ITEM_TABLE + "(" +
            COLUMN_TITLE + "," + COLUMN_DESCRIPTION + "," +
            COLUMN_TOP + "," + COLUMN_IMAGE + "," + COLUMN_PLACE + ")" +
            "VALUES(?,?,?,?,?)";
    private static final String SQL_SELECT_ALL_ITEMS_BY_TOPID = "SELECT " + COLUMN_ID + ", " +
            COLUMN_TITLE + ", " +
            COLUMN_DESCRIPTION + ", " +
            COLUMN_TOP + ", " +
            COLUMN_IMAGE + ", " +
            COLUMN_PLACE + " FROM " +
            ITEM_TABLE + " WHERE " +
            COLUMN_TOP + " like ? ORDER BY " +
            COLUMN_PLACE;
    private static final String SQL_SELECT_BY_ID = "SELECT " + COLUMN_ID + ", " +
            COLUMN_TITLE + ", " +
            COLUMN_IMAGE + ", " +
            COLUMN_TOP + ", " +
            COLUMN_DESCRIPTION + ", " +
            COLUMN_PLACE + " FROM " +
            ITEM_TABLE + " WHERE " +
            COLUMN_ID + " like ?";
    private static final String SQL_SELECT_BY_TOP_PLACE = "SELECT " + COLUMN_ID + ", " +
            COLUMN_TITLE + ", " +
            COLUMN_IMAGE + ", " +
            COLUMN_TOP + ", " +
            COLUMN_DESCRIPTION + ", " +
            COLUMN_PLACE + " FROM " +
            ITEM_TABLE + " WHERE " +
            COLUMN_TOP + " =? AND " +
            COLUMN_PLACE + " =? ";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM " + ITEM_TABLE +
            " where " + COLUMN_ID + " =?";
    private static final String SQL_DELETE_BY_TOP_ID = "DELETE FROM " + ITEM_TABLE +
            " where " + COLUMN_TOP + " =?";
    private static final String SQL_UPDATE_BY_ID = "UPDATE " + ITEM_TABLE + " SET " +
            COLUMN_TITLE + " =?, " +
            COLUMN_DESCRIPTION + " =? " +
            " WHERE " + COLUMN_ID + " =? ";
    private static final String SQL_SELECT_MAX_PLACE = "SELECT max(" +
            COLUMN_PLACE + " ) as " +
            COLUMN_PLACE + " from " +
            ITEM_TABLE + " where " +
            COLUMN_TOP + " =?";
    private static final String SQL_UPDATE_ITEMS_ABOVE_GIVEN = "UPDATE " + ITEM_TABLE + " SET " +
            COLUMN_PLACE + " = " + COLUMN_PLACE +
            "-1 WHERE " + COLUMN_TOP + " =? AND " +
            COLUMN_PLACE + " >?";
    private static final String SQL_UPDATE_ITEM_PLACE = "UPDATE " + ITEM_TABLE + " SET " +
            COLUMN_PLACE + " =? WHERE " + COLUMN_ID + " =?";


    @Override
    public void create(Item item) throws DaoException {
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_CREATE)) {
            preparedStatement.setString(1, item.getTitle());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setLong(3, item.getTop());
            preparedStatement.setString(4, item.getImage());
            preparedStatement.setInt(5, item.getPlace());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARN, e.getMessage());
            throw new DaoException(e);
        }
    }

    public void delete(long id) throws DaoException {
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARN, e.getMessage());
            throw new DaoException(e);
        }

    }

    public void deleteByTop(long top) throws DaoException {
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_TOP_ID)) {
            preparedStatement.setLong(1, top);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARN, e.getMessage());
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
                item.setTitle(resultSet.getString(COLUMN_TITLE));
                item.setDescription(resultSet.getString(COLUMN_DESCRIPTION));
                item.setImage(resultSet.getString(COLUMN_IMAGE));
                item.setTop(resultSet.getLong(COLUMN_TOP));
                item.setId(resultSet.getLong(COLUMN_ID));
                item.setPlace(resultSet.getInt(COLUMN_PLACE));
                items.add(item);
            }
        } catch (SQLException e) {
            logger.log(Level.WARN, e.getMessage());
            throw new DaoException(e);
        }
        return items;
    }

    @Override
    public Optional<Item> findItemById(long id) throws DaoException {
        Optional<Item> itemOptional = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Item item = new Item();
                item.setTitle(resultSet.getString(COLUMN_TITLE));
                item.setDescription(resultSet.getString(COLUMN_DESCRIPTION));
                item.setImage(resultSet.getString(COLUMN_IMAGE));
                item.setTop(resultSet.getLong(COLUMN_TOP));
                item.setId(resultSet.getLong(COLUMN_ID));
                item.setPlace(resultSet.getInt(COLUMN_PLACE));
                itemOptional = Optional.of(item);
            }
        } catch (SQLException e) {
            logger.log(Level.WARN, e.getMessage());
            throw new DaoException(e);
        }
        return itemOptional;
    }

    @Override
    public Optional<Item> findItemByTopPlace(long top, int place) throws DaoException {
        Optional<Item> itemOptional = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_TOP_PLACE)) {
            statement.setLong(1, top);
            statement.setInt(2, place);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Item item = new Item();
                item.setTitle(resultSet.getString(COLUMN_TITLE));
                item.setDescription(resultSet.getString(COLUMN_DESCRIPTION));
                item.setImage(resultSet.getString(COLUMN_IMAGE));
                item.setTop(resultSet.getLong(COLUMN_TOP));
                item.setId(resultSet.getLong(COLUMN_ID));
                item.setPlace(resultSet.getInt(COLUMN_PLACE));
                itemOptional = Optional.of(item);
            }
        } catch (SQLException e) {
            logger.log(Level.WARN, e.getMessage());
            throw new DaoException(e);
        }
        return itemOptional;
    }

    @Override
    public void update(Item item) throws DaoException {
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_ID)) {
            preparedStatement.setString(1, item.getTitle());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setLong(3, item.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARN, e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public int findMaxPlace(long topId) throws DaoException {
        int place = 0;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_MAX_PLACE)) {
            statement.setLong(1, topId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                place = resultSet.getInt(COLUMN_PLACE);
            }
        } catch (SQLException e) {
            logger.log(Level.WARN, e.getMessage());
            throw new DaoException(e);
        }
        return place;
    }

    @Override
    public void updateItemsAboveGiven(long top, int place) throws DaoException {
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ITEMS_ABOVE_GIVEN)) {
            statement.setLong(1, top);
            statement.setInt(2, place);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARN, e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public void changePlace(long itemId, int newPlace) throws DaoException {
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ITEM_PLACE)) {
            statement.setInt(1, newPlace);
            statement.setLong(2, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARN, e.getMessage());
            throw new DaoException(e);
        }
    }
}
