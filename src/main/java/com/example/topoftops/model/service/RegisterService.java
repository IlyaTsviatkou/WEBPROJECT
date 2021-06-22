package com.example.topoftops.model.service;

import com.example.topoftops.exception.DaoException;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.dao.impl.UserDaoImpl;
import com.example.topoftops.entity.CustomUser;
import com.example.topoftops.util.Encryptor;
import com.example.topoftops.validation.UserInfoValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class RegisterService {
    private static final Logger logger = LogManager.getLogger();
    private UserDaoImpl dao = new UserDaoImpl();
    public boolean register(CustomUser user) throws ServiceException {
        boolean result = true;
        if(UserInfoValidator.isValidPassword(user.getPassword()) && UserInfoValidator.isValidLogin(user.getLogin())) {
            String encryptedPass = Encryptor.encrypt(user.getPassword());
            user.setPassword(encryptedPass);
            try {
                dao.register(user);
            } catch (Exception e) {
                logger.log(Level.WARN,e.getMessage());
                result = false;
                throw new ServiceException(e);
            }
        } else {
           result = false;
        }
        return result;
    }
}
