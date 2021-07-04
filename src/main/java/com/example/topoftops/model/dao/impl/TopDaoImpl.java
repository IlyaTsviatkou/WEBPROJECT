package com.example.topoftops.model.dao.impl;

import com.example.topoftops.entity.Top;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.model.dao.ColumnName;
import com.example.topoftops.model.dao.TopDao;
import com.example.topoftops.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.topoftops.model.dao.ColumnName.*;

public class TopDaoImpl implements TopDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_INSERT_CREATE = "INSERT INTO " + TOP_TABLE + "(" +
            COLUMN_TITLE + "," + COLUMN_DESCRIPTION + "," +
            COLUMN_USER + "," + COLUMN_IMAGE + ")" +
            "VALUES(?,?,?,?)";
    private static final String SQL_SELECT_ALL_TOPS_BY_USERID = "SELECT " + COLUMN_ID + ", " +
            COLUMN_TITLE + ", " +
            COLUMN_DESCRIPTION + ", " +
            COLUMN_USER + ", " +
            COLUMN_RATING + "," +
            COLUMN_IMAGE + " FROM " +
            TOP_TABLE + " WHERE " +
            COLUMN_USER + " like ?";
    private static final String SQL_SELECT_ALL_TOPS = "SELECT " + COLUMN_ID + ", " +
            COLUMN_TITLE + ", " +
            COLUMN_DESCRIPTION + ", " +
            COLUMN_USER + ", " +
            COLUMN_RATING + "," +
            COLUMN_IMAGE + " FROM " +
            TOP_TABLE;
    private static final String SQL_SELECT_BY_TITLE = "SELECT " + COLUMN_ID + ", " +
            COLUMN_TITLE + ", " +
            COLUMN_IMAGE + ", " +
            COLUMN_USER + ", " +
            COLUMN_RATING + "," +
            COLUMN_DESCRIPTION + " FROM " +
            TOP_TABLE + " WHERE " +
            COLUMN_TITLE + " like ?";
    private static final String SQL_SELECT_BY_ID = "SELECT " + COLUMN_ID + ", " +
            COLUMN_TITLE + ", " +
            COLUMN_IMAGE + ", " +
            COLUMN_USER + ", " +
            COLUMN_RATING + "," +
            COLUMN_DESCRIPTION + " FROM " +
            TOP_TABLE + " WHERE " +
            COLUMN_ID + " like ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM " + TOP_TABLE +
            " where " + COLUMN_ID + " =?";
    private static final String SQL_DELETE_BY_USER_ID = "DELETE FROM " + TOP_TABLE +
            " where " + COLUMN_USER + " =?";
    private static final String SQL_UPDATE_BY_ID = "UPDATE " + TOP_TABLE + " SET " +
            COLUMN_TITLE + " =?," +
            COLUMN_DESCRIPTION + " =?," +
            COLUMN_IMAGE + " =?," +
            " WHERE " + COLUMN_ID + " =?";
    private static final String SQL_UPDATE_RATING = "UPDATE " + TOP_TABLE + " SET " +
            COLUMN_RATING + " = " + COLUMN_RATING + " + ? WHERE " + COLUMN_ID + "=?";
    private static final String SQL_SELECT_TOPS_FOR_SEARCH = "SELECT " + COLUMN_ID + ", " +
            COLUMN_TITLE + ", " +
            COLUMN_IMAGE + ", " +
            COLUMN_USER + ", " +
            COLUMN_RATING + "," +
            COLUMN_DESCRIPTION + " FROM " +
            TOP_TABLE + " WHERE " +
            COLUMN_TITLE + " like ? or " +
            COLUMN_DESCRIPTION + " like ?";
    private static final String SQL_SELECT_TOPS_FOR_SEARCH_ASC = "SELECT " + COLUMN_ID + ", " +
            COLUMN_TITLE + ", " +
            COLUMN_IMAGE + ", " +
            COLUMN_USER + ", " +
            COLUMN_RATING + "," +
            COLUMN_DESCRIPTION + " FROM " +
            TOP_TABLE + " WHERE " +
            COLUMN_TITLE + " like ? or " +
            COLUMN_DESCRIPTION + " like ? ORDER BY " +
            COLUMN_RATING;
    private static final String SQL_SELECT_TOPS_FOR_SEARCH_DESC = "SELECT " + COLUMN_ID + ", " +
            COLUMN_TITLE + ", " +
            COLUMN_IMAGE + ", " +
            COLUMN_USER + ", " +
            COLUMN_RATING + "," +
            COLUMN_DESCRIPTION + " FROM " +
            TOP_TABLE + " WHERE " +
            COLUMN_TITLE + " like ? or " +
            COLUMN_DESCRIPTION + " like ? ORDER BY " +
            COLUMN_RATING + " DESC ";


    @Override
    public void create(Top top) throws DaoException {
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_CREATE)) {
            preparedStatement.setString(1, top.getTitle());
            preparedStatement.setString(2, top.getDescription());
            preparedStatement.setLong(3, top.getUser());
            preparedStatement.setString(4, top.getImage());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(long id) throws DaoException {
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Top> findAllTops() throws DaoException {
        ArrayList<Top> tops = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_TOPS);
            while (resultSet.next()) {
                Top top = new Top();
                top.setTitle(resultSet.getString(COLUMN_TITLE));
                top.setDescription(resultSet.getString(COLUMN_DESCRIPTION));
                top.setImage(resultSet.getString(COLUMN_IMAGE));
                top.setUser(resultSet.getLong(COLUMN_USER));
                top.setId(resultSet.getLong(COLUMN_ID));
                tops.add(top);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return tops;
    }

    @Override
    public List<Top> findAllTopsByUser(long id) throws DaoException {
        ArrayList<Top> tops = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_TOPS_BY_USERID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Top top = new Top();
                top.setTitle(resultSet.getString(COLUMN_TITLE));
                top.setDescription(resultSet.getString(COLUMN_DESCRIPTION));
                top.setImage(resultSet.getString(COLUMN_IMAGE));
                top.setUser(resultSet.getLong(COLUMN_USER));
                top.setId(resultSet.getLong(COLUMN_ID));
                tops.add(top);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return tops;
    }

    @Override
    public Top findTopByTitle(String title) throws DaoException {
        Top top = null;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_TITLE)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                top = new Top();
                top.setTitle(resultSet.getString(COLUMN_TITLE));
                top.setDescription(resultSet.getString(COLUMN_DESCRIPTION));
                top.setImage(resultSet.getString(COLUMN_IMAGE));
                top.setUser(resultSet.getLong(COLUMN_USER));
                top.setId(resultSet.getLong(COLUMN_ID));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return top;
    }

    @Override
    public Top findTopByID(long id) throws DaoException {
        Top top = null;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                top = new Top();
                top.setTitle(resultSet.getString(COLUMN_TITLE));
                top.setDescription(resultSet.getString(COLUMN_DESCRIPTION));
                top.setImage(resultSet.getString(COLUMN_IMAGE));
                top.setUser(resultSet.getLong(COLUMN_USER));
                top.setId(resultSet.getLong(COLUMN_ID));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return top;
    }

    @Override
    public void updateRating(int mark, long id) throws DaoException {

        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_RATING)) {
            statement.setInt(1, mark);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }

    }

    @Override
    public List<Top> searchInTops(String data) throws DaoException {
        ArrayList<Top> tops = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TOPS_FOR_SEARCH)) {
            statement.setString(1, "%" + data + "%");
            statement.setString(2, "%" + data + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Top top = new Top();
                top.setTitle(resultSet.getString(COLUMN_TITLE));
                top.setDescription(resultSet.getString(COLUMN_DESCRIPTION));
                top.setImage(resultSet.getString(COLUMN_IMAGE));
                top.setUser(resultSet.getLong(COLUMN_USER));
                top.setId(resultSet.getLong(COLUMN_ID));
                tops.add(top);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return tops;
    }

    @Override
    public List<Top> searchInTopsWithRatingUp(String data) throws DaoException {
        ArrayList<Top> tops = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TOPS_FOR_SEARCH_ASC)) {
            statement.setString(1, "%" + data + "%");
            statement.setString(2, "%" + data + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Top top = new Top();
                top.setTitle(resultSet.getString(COLUMN_TITLE));
                top.setDescription(resultSet.getString(COLUMN_DESCRIPTION));
                top.setImage(resultSet.getString(COLUMN_IMAGE));
                top.setUser(resultSet.getLong(COLUMN_USER));
                top.setId(resultSet.getLong(COLUMN_ID));
                tops.add(top);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return tops;
    }

    @Override
    public List<Top> searchInTopsWithRatingDown(String data) throws DaoException {
        ArrayList<Top> tops = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TOPS_FOR_SEARCH_DESC)) {
            statement.setString(1, "%" + data + "%");
            statement.setString(2, "%" + data + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Top top = new Top();
                top.setTitle(resultSet.getString(COLUMN_TITLE));
                top.setDescription(resultSet.getString(COLUMN_DESCRIPTION));
                top.setImage(resultSet.getString(COLUMN_IMAGE));
                top.setUser(resultSet.getLong(COLUMN_USER));
                top.setId(resultSet.getLong(COLUMN_ID));
                tops.add(top);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return tops;
    }
}


