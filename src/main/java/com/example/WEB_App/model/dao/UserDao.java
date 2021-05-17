package com.example.WEB_App.model.dao;

import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<CustomUser> login(String login, String pass) throws DaoException, SQLException;

    void register(CustomUser user) throws DaoException,SQLException;

    List<CustomUser> findUsersByName(String userName) throws DaoException,SQLException;

    List<CustomUser> findAllUsers() throws DaoException,SQLException;
}
