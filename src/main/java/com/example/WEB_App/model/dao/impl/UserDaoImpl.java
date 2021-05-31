package com.example.WEB_App.model.dao.impl;

import com.example.WEB_App.exception.ConnectionPoolException;
import com.example.WEB_App.model.dao.Const;
import com.example.WEB_App.model.dao.UserDao;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.exception.DaoException;
import com.example.WEB_App.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao { //todo result and statement to try or close it
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_SELECT_LOGIN = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
            Const.COLUMN_USER_LOGIN + "=? AND " +
            Const.COLUMN_USER_PASSWORD + "=?" ;
    private static final String SQL_INSERT_REGISTER = "INSERT INTO " + Const.USER_TABLE + " (" +
            Const.COLUMN_USER_ID + "," +
            Const.COLUMN_USER_LOGIN + "," + Const.COLUMN_USER_PASSWORD + "," +
            Const.COLUMN_USER_EMAIL + "," + Const.COLUMN_USER_ROLE + "," +
            Const.COLUMN_USER_STATUS + ")" +
            "VALUES(?,?,?,?,?,?)" + " ON DUPLICATE KEY UPDATE " +
            Const.COLUMN_USER_LOGIN + " =?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT " + Const.COLUMN_USER_ID + ", " +
            Const.COLUMN_USER_LOGIN + ", " +
            Const.COLUMN_USER_EMAIL + " FROM " +
            Const.USER_TABLE;
    private static final String SQL_SELECT_USERS_BY_NAME = "SELECT " + Const.COLUMN_USER_ID + ", " +
            Const.COLUMN_USER_LOGIN + ", " +
            Const.COLUMN_USER_EMAIL + " FROM " +
            Const.USER_TABLE + " WHERE " +
            Const.COLUMN_USER_LOGIN + " like ?";
    private static final String SQL_UPDATE_STATUS = "UPDATE " + Const.USER_TABLE + " SET " +
            Const.COLUMN_USER_STATUS + " =?  WHERE " + Const.COLUMN_USER_ID + "=? AND " + Const.COLUMN_USER_STATUS + "=?";


    @Override
    public Optional<CustomUser> login(String login, String pass) throws DaoException, SQLException {
        Optional<CustomUser> user = Optional.empty();
        PreparedStatement prSt = null;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection()) {
            ResultSet resultSet ;
            prSt = connection.prepareStatement(SQL_SELECT_LOGIN);
            prSt.setString(1,login);
            prSt.setString(2,pass);
            resultSet =  prSt.executeQuery();
            if(resultSet!=null) {
                    while (resultSet.next()) {
                        String email = resultSet.getString(Const.COLUMN_USER_EMAIL);
                        int role = resultSet.getInt(Const.COLUMN_USER_ROLE);
                        int status = resultSet.getInt(Const.COLUMN_USER_STATUS);
                        user = Optional.of(new CustomUser(resultSet.getLong(Const.COLUMN_USER_ID),
                                login, pass,
                                email, role, status));
                    }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            if (prSt != null) {
                prSt.close();
            }

        }
        return user;
    }

    @Override
    public void register(CustomUser user) throws DaoException, SQLException {
        PreparedStatement prSt= null;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection()) {
            prSt= connection.prepareStatement(SQL_INSERT_REGISTER);
            prSt.setLong(1, user.getId());
            prSt.setString(2, user.getLogin());
            prSt.setString(3, user.getPassword());
            prSt.setString(4, user.getEmail());
            prSt.setInt(5, user.getRole());
            prSt.setInt(6,user.getStatus());
            prSt.setString(7,user.getLogin());
            prSt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (prSt != null) {
                prSt.close();
            }
        }
        //fixme this is for single user in table
//        numberUpdatedRows = statement.executeUpdate();
//        return number
    }

    @Override
    public List<CustomUser> findAllUsers() throws DaoException {
        List<CustomUser> users = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                CustomUser user = new CustomUser();
                user.setId(resultSet.getInt(Const.COLUMN_USER_ID));
                user.setEmail(resultSet.getString(Const.COLUMN_USER_EMAIL));
                user.setLogin(resultSet.getString(Const.COLUMN_USER_LOGIN));
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
        try (Connection connection = CustomConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USERS_BY_NAME);
            statement.setString(1, userName + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CustomUser user = new CustomUser();
                user.setId(resultSet.getInt(Const.COLUMN_USER_ID));
                user.setEmail(resultSet.getString(Const.COLUMN_USER_EMAIL));
                user.setLogin(resultSet.getString(Const.COLUMN_USER_LOGIN));
                user.setRole(resultSet.getInt(Const.COLUMN_USER_ROLE));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public boolean updateStatus(long id, int statusTo, int statusFrom) {
        int numberUpdatedRows = 0;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATUS)) {
            statement.setInt(1, statusTo);
            statement.setLong(2, id);
            statement.setInt(3, statusFrom);
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR,e);
        }
        return numberUpdatedRows != 0;
    }
}
