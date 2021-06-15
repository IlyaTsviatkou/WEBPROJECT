package com.example.topoftops.model.dao.impl;

import com.example.topoftops.entity.Top;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.model.dao.ColumnName;
import com.example.topoftops.model.dao.TopDao;
import com.example.topoftops.model.pool.CustomConnectionPool;

import java.sql.*;
import java.util.ArrayList;

import static com.example.topoftops.model.dao.ColumnName.*;

public class TopDaoImpl implements TopDao {
    private static final String SQL_INSERT_CREATE = "INSERT INTO " +  TOP_TABLE + "(" +
             COLUMN_TOP_TITLE + "," +  COLUMN_TOP_DESCRIPTION + "," +
             COLUMN_TOP_USER + "," +  COLUMN_TOP_IMAGE + ")" +
            "VALUES(?,?,?,?)";
    private static final String SQL_SELECT_ALL_TOPS_BY_USERID = "SELECT " +  COLUMN_TOP_ID + ", " +
             COLUMN_TOP_TITLE + ", " +
             COLUMN_TOP_DESCRIPTION + ", " +
             COLUMN_TOP_USER + ", " +
             COLUMN_TOP_IMAGE + " FROM " +
             TOP_TABLE + " WHERE " +
             COLUMN_TOP_USER + " like ?";
    private static final String SQL_SELECT_TOP_BY_TITLE = "SELECT " +  COLUMN_TOP_ID + ", " +
             COLUMN_TOP_TITLE + ", " +
             COLUMN_TOP_IMAGE + ", " +
             COLUMN_TOP_USER + ", " +
             COLUMN_TOP_DESCRIPTION + " FROM " +
             TOP_TABLE + " WHERE " +
             COLUMN_TOP_TITLE + " like ?";
    private static final String SQL_SELECT_TOP_BY_ID = "SELECT " +  COLUMN_TOP_ID + ", " +
             COLUMN_TOP_TITLE + ", " +
             COLUMN_TOP_IMAGE + ", " +
             COLUMN_TOP_USER + ", " +
             COLUMN_TOP_DESCRIPTION + " FROM " +
             TOP_TABLE + " WHERE " +
             COLUMN_TOP_ID + " like ?";
    private static final String SQL_DELETE_TOP_BY_ID ="DELETE FROM " +  TOP_TABLE +
            " where " +  COLUMN_TOP_ID + " =?";
    private static final String SQL_DELETE_TOP_BY_USER_ID ="DELETE FROM " +  TOP_TABLE +
            " where " +  COLUMN_TOP_USER + " =?";
    private static final String SQL_UPDATE_TOP_BY_ID ="UPDATE " +  TOP_TABLE + " SET " +
             COLUMN_TOP_TITLE + " =?," +
             COLUMN_TOP_DESCRIPTION + " =?," +
             COLUMN_TOP_IMAGE + " =?," +
            " WHERE " +  COLUMN_TOP_ID + " =?";


    @Override
    public void create(Top top) throws DaoException{
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
        PreparedStatement  prSt = connection.prepareStatement(SQL_INSERT_CREATE)){
            prSt.setString(1, top.getTitle());
            prSt.setString(2, top.getDescription());
            prSt.setLong(3, top.getUser());
            prSt.setString(4, top.getImage());
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public ArrayList<Top> findAllTops(long id) throws DaoException {
        ArrayList<Top> tops = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_TOPS_BY_USERID);
            while (resultSet.next()) {
                Top top = new Top();
                top.setTitle(resultSet.getString( COLUMN_TOP_TITLE));
                top.setDescription(resultSet.getString( COLUMN_TOP_DESCRIPTION));
                top.setImage(resultSet.getString( COLUMN_TOP_IMAGE));
                top.setUser(resultSet.getLong( COLUMN_TOP_USER));
                top.setId(resultSet.getLong( COLUMN_TOP_ID));
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
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TOP_BY_TITLE)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                top.setTitle(resultSet.getString( COLUMN_TOP_TITLE));
                top.setDescription(resultSet.getString( COLUMN_TOP_DESCRIPTION));
                top.setImage(resultSet.getString( COLUMN_TOP_IMAGE));
                top.setUser(resultSet.getLong( COLUMN_TOP_USER));
                top.setId(resultSet.getLong( COLUMN_TOP_ID));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return top;
    }

    @Override
    public Top findTopByID(long id) throws DaoException {
        Top top = new Top();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TOP_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                top.setTitle(resultSet.getString( COLUMN_TOP_TITLE));
                top.setDescription(resultSet.getString( COLUMN_TOP_DESCRIPTION));
                top.setImage(resultSet.getString( COLUMN_TOP_IMAGE));
                top.setUser(resultSet.getLong( COLUMN_TOP_USER));
                top.setId(resultSet.getLong( COLUMN_TOP_ID));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return top;
    }
}


