package com.example.WEB_App.model.service;

import com.example.WEB_App.model.dao.impl.UserDaoImpl;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.util.Encryptor;
import com.example.WEB_App.validation.Validation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class RegisterService {
    public static Logger logger = LogManager.getLogger();
    UserDaoImpl dao = new UserDaoImpl();
    public boolean register(CustomUser user) throws SQLException, ClassNotFoundException {
        boolean result = true;
        if(Validation.isValidPassword(user.getPassword()) && Validation.isValidLogin(user.getLogin())) {
            String encryptedPass = Encryptor.encrypt(user.getPassword());
            user.setPassword(encryptedPass);
            try {
                dao.register(user);
            } catch (Exception e) {
                logger.log(Level.WARN,e.getMessage());
                result = false;
            }
        } else {
           result = false;
        }
        return result;
    }
}
