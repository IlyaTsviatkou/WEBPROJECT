package com.example.topoftops.model.service;

import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.dao.impl.UserDaoImpl;
import com.example.topoftops.entity.CustomUser;
import com.example.topoftops.exception.DaoException;


import java.util.List;

public class AdminService {
    private UserDaoImpl dao = new UserDaoImpl();

    public List<CustomUser> findUsersByLogin() throws ServiceException {
        try {
            return dao.findUsersByLogin();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<CustomUser> findUsersByName(String userName) throws ServiceException {
        try {
            return dao.findUsersByName(userName);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
