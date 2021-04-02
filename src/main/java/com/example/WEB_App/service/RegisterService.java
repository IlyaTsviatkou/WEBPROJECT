package com.example.WEB_App.service;

import com.example.WEB_App.dao.impl.UserDaoImpl;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.util.Encryptor;
import com.example.WEB_App.validation.Validation;

import java.sql.SQLException;

public class RegisterService {

    public boolean register(CustomUser user) throws SQLException, ClassNotFoundException {
        boolean result = true;
        UserDaoImpl dao = new UserDaoImpl();
        if(Validation.isValidPassword(user.getPassword()) && Validation.isValidLogin(user.getLogin())) {
            String encryptedPass = Encryptor.encrypt(user.getPassword());
            user.setPassword(encryptedPass);
            try {
                dao.register(user);
            } catch (Exception e) {
                e.printStackTrace();
                result = false;
            }
        } else {
           result = false;
        }
        return result;
    }
}
