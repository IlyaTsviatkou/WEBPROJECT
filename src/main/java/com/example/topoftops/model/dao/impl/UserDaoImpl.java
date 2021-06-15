package com.example.topoftops.model.dao.impl;

import com.example.topoftops.model.dao.ColumnName;
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
    private static final String SQL_SELECT_LOGIN = "SELECT * FROM " +  USER_TABLE + " WHERE " +
             COLUMN_USER_LOGIN + "=? AND " +
             COLUMN_USER_PASSWORD + "=?" ;
    private static final String SQL_INSERT_REGISTER = "INSERT INTO " +  USER_TABLE + " (" +
             COLUMN_USER_LOGIN + "," +  COLUMN_USER_PASSWORD + "," +
             COLUMN_USER_EMAIL + "," +  COLUMN_USER_ROLE + "," +
             COLUMN_USER_STATUS + ")" +
            "VALUES(?,?,?,?,?)" + " ON DUPLICATE KEY UPDATE " +
             COLUMN_USER_LOGIN + " =?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT " +  COLUMN_USER_ID + ", " +
             COLUMN_USER_LOGIN + ", " +
             COLUMN_USER_EMAIL + " FROM " +
             USER_TABLE;
    private static final String SQL_SELECT_USERS_BY_NAME = "SELECT " +  COLUMN_USER_ID + ", " +
             COLUMN_USER_LOGIN + ", " +
             COLUMN_USER_EMAIL + " FROM " +
             USER_TABLE + " WHERE " +
             COLUMN_USER_LOGIN + " like ?";
    private static final String SQL_UPDATE_STATUS = "UPDATE " +  USER_TABLE + " SET " +
             COLUMN_USER_STATUS + " =?  WHERE " +  COLUMN_USER_LOGIN + "=? AND " +  COLUMN_USER_STATUS + "=?";


    @Override
    public Optional<CustomUser> login(String login, String pass) throws DaoException{
        Optional<CustomUser> user = Optional.empty();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
         PreparedStatement prSt = connection.prepareStatement(SQL_SELECT_LOGIN)) {
            ResultSet resultSet;
            prSt.setString(1,login);
            prSt.setString(2,pass);
            resultSet =  prSt.executeQuery();
            if(resultSet!=null) {
                    while (resultSet.next()) {
                        String email = resultSet.getString( COLUMN_USER_EMAIL);
                        int role = resultSet.getInt( COLUMN_USER_ROLE);
                        int status = resultSet.getInt( COLUMN_USER_STATUS);
                        user = Optional.of(new CustomUser(resultSet.getLong( COLUMN_USER_ID),
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
    public void register(CustomUser user) throws DaoException{

        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement  prSt= connection.prepareStatement(SQL_INSERT_REGISTER)) {
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, user.getEmail());
            prSt.setInt(4, user.getRole());
            prSt.setInt(5,user.getStatus());
            prSt.setString(6 ,user.getLogin());
            prSt.executeUpdate();
        }catch (SQLException e) {
            throw new DaoException(e);
        }
        //fixme this is for single user in table
//        numberUpdatedRows = statement.executeUpdate();
//        return number
    }

    @Override
    public List<CustomUser> findAllUsers() throws DaoException {
        List<CustomUser> users = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                CustomUser user = new CustomUser();
                user.setId(resultSet.getInt( COLUMN_USER_ID));
                user.setEmail(resultSet.getString( COLUMN_USER_EMAIL));
                user.setLogin(resultSet.getString( COLUMN_USER_LOGIN));
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
            statement.setString(1, userName + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CustomUser user = new CustomUser();
                user.setId(resultSet.getInt( COLUMN_USER_ID));
                user.setEmail(resultSet.getString( COLUMN_USER_EMAIL));
                user.setLogin(resultSet.getString( COLUMN_USER_LOGIN));
                user.setRole(resultSet.getInt( COLUMN_USER_ROLE));
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
            logger.log(Level.ERROR,e);
            throw new DaoException(e);
        }
        return numberUpdatedRows != 0;
    }
}
