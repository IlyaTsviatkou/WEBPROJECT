package com.example.WEB_App.model.service;

import com.example.WEB_App.model.dao.impl.UserDaoImpl;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.exception.DaoException;


import java.util.List;

public class SortService {
    UserDaoImpl dao = new UserDaoImpl();

    public List<CustomUser> findAllUsers() throws DaoException {
        return dao.findAllUsers();
    }

    public List<CustomUser> findUsersByName(String userName) throws DaoException {
        return dao.findUsersByName(userName);
    }
}
