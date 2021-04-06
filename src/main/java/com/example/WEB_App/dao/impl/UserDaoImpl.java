package com.example.WEB_App.dao.impl;

import com.example.WEB_App.dao.Const;
import com.example.WEB_App.dao.UserDao;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.exception.DaoException;
import com.example.WEB_App.pool.CustomConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final String SQL_SELECT_LOGIN = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
            Const.COLUMN_USER_LOGIN + "=? AND " +
            Const.COLUMN_USER_PASSWORD + "=?" ;
    private static final String SQL_INSERT_REGISTER = "INSERT INTO " + Const.USER_TABLE + "(" +
            Const.COLUMN_USER_ID + "," +
            Const.COLUMN_USER_LOGIN + "," + Const.COLUMN_USER_PASSWORD + "," +
            Const.COLUMN_USER_INFORMATION + ")" +
            "VALUES(?,?,?,?)";
    private static final String SQL_SELECT_ALL_USERS = "SELECT " + Const.COLUMN_USER_ID + ", " +
            Const.COLUMN_USER_LOGIN + ", " +
            Const.COLUMN_USER_INFORMATION + " FROM " +
            Const.USER_TABLE;
    private static final String SQL_SELECT_USERS_BY_NAME = "SELECT " + Const.COLUMN_USER_ID + ", " +
            Const.COLUMN_USER_LOGIN + ", " +
            Const.COLUMN_USER_INFORMATION + " FROM " +
            Const.USER_TABLE + " WHERE " +
            Const.COLUMN_USER_LOGIN + " like ?";


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
                        String inform = resultSet.getString(Const.COLUMN_USER_INFORMATION);
                        user = Optional.of(new CustomUser(resultSet.getLong(Const.COLUMN_USER_ID),
                                login, pass,
                                inform));
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
            prSt.setString(4, user.getInformation());
            prSt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (prSt != null) {
                prSt.close();
            }
        }

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
                user.setInformation(resultSet.getString(Const.COLUMN_USER_INFORMATION));
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
                user.setInformation(resultSet.getString(Const.COLUMN_USER_INFORMATION));
                user.setLogin(resultSet.getString(Const.COLUMN_USER_LOGIN));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return users;
    }
}
