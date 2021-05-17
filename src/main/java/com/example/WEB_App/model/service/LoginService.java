package com.example.WEB_App.model.service;

import com.example.WEB_App.model.dao.impl.UserDaoImpl;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.exception.DaoException;
import com.example.WEB_App.util.Encryptor;
import com.example.WEB_App.validation.Validation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Optional;

public class LoginService {
    public static Logger logger = LogManager.getLogger();
    UserDaoImpl dao = new UserDaoImpl();
    public Optional<CustomUser> login(String login, String password)  {
        //todo where should i check if user is null????
        Optional<CustomUser> user = Optional.empty();
        if(Validation.isValidPassword(password) && Validation.isValidLogin(login)) {
            String encryptedPass = Encryptor.encrypt(password);
            try {
                user = dao.login(login, encryptedPass);
            } catch (DaoException | SQLException e) {
                logger.log(Level.WARN,e.getMessage());
            }
        }
        return user;
    }

}
