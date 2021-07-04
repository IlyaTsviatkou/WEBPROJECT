package com.example.topoftops.model.dao.impl;

import com.example.topoftops.entity.Mark;
import com.example.topoftops.entity.Top;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.model.dao.MarkDao;
import com.example.topoftops.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.topoftops.model.dao.ColumnName.*;

public class MarkDaoImpl implements MarkDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_INSERT_CREATE = "INSERT INTO " + MARK_TABLE + "(" +
            COLUMN_TOP + "," +
            COLUMN_USER + "," + COLUMN_MARK + ")" +
            "VALUES(?,?,?)" + " ON DUPLICATE KEY UPDATE " +
            COLUMN_MARK + " =?";
    private static final String SQL_SELECT_COUNT_MARK = " SELECT Count(id) FROM " +
            MARK_TABLE + " WHERE " +
            COLUMN_USER + " =? AND " +
            COLUMN_TOP + " =?  AND " +
            COLUMN_MARK + "=?";

    @Override
    public boolean create(Mark mark) throws DaoException {
        int numberEffectedRows = 0;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_CREATE)) {
            preparedStatement.setLong(1, mark.getTop());
            preparedStatement.setLong(2, mark.getUser());
            preparedStatement.setInt(3, mark.getMark());
            preparedStatement.setInt(4, mark.getMark());
            numberEffectedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARN,e);
            throw new DaoException(e);
        }
        return (numberEffectedRows != 2);
    }

    @Override
    public boolean isExist(Mark mark) throws DaoException {
        int count = 0;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COUNT_MARK)) {
            preparedStatement.setLong(1, mark.getUser());
            preparedStatement.setLong(2, mark.getTop());
            preparedStatement.setInt(3, mark.getMark());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(COLUMN_COUNT_ID);
            }
        } catch (SQLException e) {
            logger.log(Level.WARN,e);
            throw new DaoException(e);
        }
        return (count == 1);
    }
}
