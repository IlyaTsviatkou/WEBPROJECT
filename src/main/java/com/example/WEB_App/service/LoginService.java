package com.example.WEB_App.service;

import com.example.WEB_App.dao.UserDao;
import com.example.WEB_App.dao.impl.UserDaoImpl;
import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.exception.DaoException;
import com.example.WEB_App.repository.Repository;
import com.example.WEB_App.repository.impl.LoginSpecification;
import com.example.WEB_App.validation.Validation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LoginService {

//    public Optional<CustomUser> Login(String login, String password) {
//        Optional<CustomUser> user;
//        Repository rep =new Repository();
//        user = rep.get(new LoginSpecification(login,password));
//        return user;
//    }

    public Optional<CustomUser> login(String login, String password) throws SQLException, DaoException {
        UserDaoImpl dao = new UserDaoImpl();//todo where should i check if user is null????
        Optional<CustomUser> user = Optional.empty();
        if(Validation.isValidPassword(password) && Validation.isValidLogin(login)) {
            String encryptedPass = Encryptor.encrypt(password);
            user = dao.login(login, encryptedPass);
        }
        return user;
    }

}
