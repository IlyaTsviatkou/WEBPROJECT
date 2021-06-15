package com.example.topoftops.model.service;

import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.dao.impl.UserDaoImpl;
import com.example.topoftops.entity.CustomUser;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.util.Encryptor;
import com.example.topoftops.validation.UserInfoValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Optional;

public class LoginService {
    private static Logger logger = LogManager.getLogger();
    private UserDaoImpl dao = new UserDaoImpl();
    public Optional<CustomUser> login(String login, String password) throws ServiceException {
        //todo where should i check if user is null????
        Optional<CustomUser> user = Optional.empty();
        if(UserInfoValidator.isValidPassword(password) && UserInfoValidator.isValidLogin(login)) {
            String encryptedPass = Encryptor.encrypt(password);
            try {
                user = dao.login(login, encryptedPass);
            } catch (DaoException e) {
                logger.log(Level.WARN,e.getMessage());
                throw new ServiceException(e);
            }
        }
        return user;
    }

}
