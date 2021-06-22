package com.example.topoftops.model.dao;

import com.example.topoftops.entity.CustomUser;
import com.example.topoftops.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<CustomUser> login(String login, String pass) throws DaoException;

    boolean register(CustomUser user) throws DaoException;

    List<CustomUser> findUsersByName(String userName) throws DaoException;

    List<CustomUser> findUsersByLogin() throws DaoException;

    boolean updateStatus(String login, int statusTo, int statusFrom) throws DaoException;

}
