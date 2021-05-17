package com.example.WEB_App.model.dao.impl;

import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.entity.Top;
import com.example.WEB_App.exception.DaoException;
import com.example.WEB_App.model.dao.Const;
import com.example.WEB_App.model.dao.TopDao;
import com.example.WEB_App.model.dao.UserDao;
import com.example.WEB_App.model.pool.CustomConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TopDaoImpl implements TopDao {
    private static final String SQL_INSERT_CREATE = "INSERT INTO " + Const.TOP_TABLE + "(" +
            Const.COLUMN_TOP_ID + "," +
            Const.COLUMN_TOP_TITLE + "," + Const.COLUMN_TOP_DESCRIPTION + "," +
            Const.COLUMN_TOP_USER + "," + Const.COLUMN_TOP_IMAGE + ")" +
            "VALUES(?,?,?,?,?)";
    private static final String SQL_SELECT_ALL_TOPS_BY_USERID = "SELECT " + Const.COLUMN_TOP_ID + ", " +
            Const.COLUMN_TOP_TITLE + ", " +
            Const.COLUMN_TOP_DESCRIPTION + ", " +
            Const.COLUMN_TOP_USER + ", " +
            Const.COLUMN_TOP_IMAGE + " FROM " +
            Const.TOP_TABLE + " WHERE " +
            Const.COLUMN_TOP_USER + " like ?";
    private static final String SQL_SELECT_TOP_BY_TITLE = "SELECT " + Const.COLUMN_TOP_ID + ", " +
            Const.COLUMN_TOP_TITLE + ", " +
            Const.COLUMN_TOP_IMAGE + ", " +
            Const.COLUMN_TOP_USER + ", " +
            Const.COLUMN_TOP_DESCRIPTION + " FROM " +
            Const.TOP_TABLE + " WHERE " +
            Const.COLUMN_TOP_TITLE + " like ?";
    private static final String SQL_SELECT_TOP_BY_ID = "SELECT " + Const.COLUMN_TOP_ID + ", " +
            Const.COLUMN_TOP_TITLE + ", " +
            Const.COLUMN_TOP_IMAGE + ", " +
            Const.COLUMN_TOP_USER + ", " +
            Const.COLUMN_TOP_DESCRIPTION + " FROM " +
            Const.TOP_TABLE + " WHERE " +
            Const.COLUMN_TOP_ID + " like ?";
    private static final String SQL_DELETE_TOP_BY_ID ="DELETE FROM " + Const.TOP_TABLE +
            " where " + Const.COLUMN_TOP_ID + " =?";
    private static final String SQL_DELETE_TOP_BY_USER_ID ="DELETE FROM " + Const.TOP_TABLE +
            " where " + Const.COLUMN_TOP_USER + " =?";
    private static final String SQL_UPDATE_TOP_BY_ID ="UPDATE " + Const.TOP_TABLE + " SET " +
            Const.COLUMN_TOP_TITLE + " =?," +
            Const.COLUMN_TOP_DESCRIPTION + " =?," +
            Const.COLUMN_TOP_IMAGE + " =?," +
            " WHERE " + Const.COLUMN_TOP_ID + " =?";


    @Override
    public void create(Top top) throws DaoException, SQLException {
        PreparedStatement prSt = null;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection()) {
            prSt = connection.prepareStatement(SQL_INSERT_CREATE);
            long id = (long) new Random().nextInt(99999) + 1;
            prSt.setLong(1, id);
            prSt.setString(2, top.getTitle());
            prSt.setString(3, top.getDescription());
            prSt.setLong(4, top.getUser());
            prSt.setString(5, top.getImage());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (prSt != null) {
                prSt.close();
            }
        }

    }

    @Override
    public ArrayList<Top> findAllTops(long id) throws DaoException {
        ArrayList<Top> tops = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_TOPS_BY_USERID);
            while (resultSet.next()) {
                Top top = new Top();
                top.setTitle(resultSet.getString(Const.COLUMN_TOP_TITLE));
                top.setDescription(resultSet.getString(Const.COLUMN_TOP_DESCRIPTION));
                top.setImage(resultSet.getString(Const.COLUMN_TOP_IMAGE));
                top.setUser(resultSet.getLong(Const.COLUMN_TOP_USER));
                top.setId(resultSet.getLong(Const.COLUMN_TOP_ID));
                tops.add(top);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return tops;
    }

    @Override
    public Top findTopByTitle(String title) throws DaoException {
        Top top = new Top();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TOP_BY_TITLE);
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                top.setTitle(resultSet.getString(Const.COLUMN_TOP_TITLE));
                top.setDescription(resultSet.getString(Const.COLUMN_TOP_DESCRIPTION));
                top.setImage(resultSet.getString(Const.COLUMN_TOP_IMAGE));
                top.setUser(resultSet.getLong(Const.COLUMN_TOP_USER));
                top.setId(resultSet.getLong(Const.COLUMN_TOP_ID));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return top;
    }

    @Override
    public Top findTopByID(long id) throws DaoException {
        Top top = new Top();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TOP_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                top.setTitle(resultSet.getString(Const.COLUMN_TOP_TITLE));
                top.setDescription(resultSet.getString(Const.COLUMN_TOP_DESCRIPTION));
                top.setImage(resultSet.getString(Const.COLUMN_TOP_IMAGE));
                top.setUser(resultSet.getLong(Const.COLUMN_TOP_USER));
                top.setId(resultSet.getLong(Const.COLUMN_TOP_ID));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return top;
    }
}


