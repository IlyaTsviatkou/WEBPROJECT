package com.example.topoftops.model.dao.impl;

import com.example.topoftops.model.dao.UserDao;
import com.example.topoftops.entity.CustomUser;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.topoftops.model.dao.ColumnName.*;

public class UserDaoImpl implements UserDao { //todo result and statement to try or close it
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_SELECT_LOGIN = "SELECT * FROM " + USER_TABLE + " WHERE " +
            COLUMN_LOGIN + "=? AND " +
            COLUMN_PASSWORD + "=?";
    private static final String SQL_INSERT_REGISTER = "INSERT INTO " + USER_TABLE + " (" +
            COLUMN_LOGIN + "," + COLUMN_PASSWORD + "," +
            COLUMN_EMAIL + "," + COLUMN_ROLE + "," +
            COLUMN_STATUS + ")" +
            "VALUES(?,?,?,?,?)" + " ON DUPLICATE KEY UPDATE " + COLUMN_ID + "=" + COLUMN_ID;
    private static final String SQL_SELECT_ALL_USERS = "SELECT " + COLUMN_ID + ", " +
            COLUMN_LOGIN + ", " +
            COLUMN_EMAIL + " FROM " +
            USER_TABLE;
    private static final String SQL_SELECT_USERS_BY_NAME = "SELECT " + COLUMN_ID + ", " +
            COLUMN_LOGIN + ", " +
            COLUMN_RATING + ", " +
            COLUMN_ROLE + ", " +
            COLUMN_EMAIL + " FROM " +
            USER_TABLE + " WHERE " +
            COLUMN_LOGIN + " like ?";
    private static final String SQL_UPDATE_STATUS = "UPDATE " + USER_TABLE + " SET " +
            COLUMN_STATUS + " =?  WHERE " + COLUMN_LOGIN + "=? AND " + COLUMN_STATUS + "=?";


    @Override
    public Optional<CustomUser> login(String login, String pass) throws DaoException {
        Optional<CustomUser> user = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_LOGIN)) {
            ResultSet resultSet;
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    String email = resultSet.getString(COLUMN_EMAIL);
                    int role = resultSet.getInt(COLUMN_ROLE);
                    int status = resultSet.getInt(COLUMN_STATUS);
                    user = Optional.of(new CustomUser(resultSet.getLong(COLUMN_ID),
                            login, pass,
                            email, role, status));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }

    @Override
    public boolean register(CustomUser user) throws DaoException {
        int numberUpdatedRows =0;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_REGISTER)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getRole());
            preparedStatement.setInt(5, user.getStatus());
            numberUpdatedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
         return (numberUpdatedRows > 1);
    }

    @Override
    public List<CustomUser> findUsersByLogin() throws DaoException {
        List<CustomUser> users = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                CustomUser user = new CustomUser();
                user.setId(resultSet.getInt(COLUMN_ID));
                user.setEmail(resultSet.getString(COLUMN_EMAIL));
                user.setLogin(resultSet.getString(COLUMN_LOGIN));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public List<CustomUser> findUsersByName(String userName) throws DaoException {
        List<CustomUser> users = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USERS_BY_NAME);) {
            statement.setString(1, "%" + userName + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CustomUser user = new CustomUser();
                user.setId(resultSet.getInt(COLUMN_ID));
                user.setEmail(resultSet.getString(COLUMN_EMAIL));
                user.setLogin(resultSet.getString(COLUMN_LOGIN));
                user.setRole(resultSet.getInt(COLUMN_ROLE));
                user.setRating(resultSet.getInt(COLUMN_RATING));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public boolean updateStatus(String login, int statusTo, int statusFrom) throws DaoException {
        int numberUpdatedRows = 0;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATUS)) {
            statement.setInt(1, statusTo);
            statement.setString(2, login);
            statement.setInt(3, statusFrom);
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
        return (numberUpdatedRows != 0);
    }
}
