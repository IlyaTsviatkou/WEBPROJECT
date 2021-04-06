package com.example.WEB_App.service;

import com.example.WEB_App.dao.impl.UserDaoImpl;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.exception.DaoException;
import com.example.WEB_App.util.Encryptor;
import com.example.WEB_App.validation.Validation;

import java.sql.SQLException;
import java.util.Optional;

public class LoginService {
    UserDaoImpl dao = new UserDaoImpl();
    public Optional<CustomUser> login(String login, String password)  {
        //todo where should i check if user is null????
        Optional<CustomUser> user = Optional.empty();
        if(Validation.isValidPassword(password) && Validation.isValidLogin(login)) {
            String encryptedPass = Encryptor.encrypt(password);
            try {
                user = dao.login(login, encryptedPass);
            } catch (DaoException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return user;
    }

}
