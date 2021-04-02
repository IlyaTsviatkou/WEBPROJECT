package com.example.WEB_App.service;

import com.example.WEB_App.comparator.UserComparator;
import com.example.WEB_App.dao.impl.UserDaoImpl;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.exception.DaoException;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SortService {
    UserDaoImpl dao = new UserDaoImpl();

    public List<CustomUser> findAllUsers() throws DaoException {
        return dao.findAllUsers();
    }

    public List<CustomUser> findUsersByName(String userName) throws DaoException {
        return dao.findUsersByName(userName);
    }
}
