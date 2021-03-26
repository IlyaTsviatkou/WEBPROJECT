package com.example.WEB_App.service;

import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.repository.Repository;
import com.example.WEB_App.repository.impl.LoginSpecification;
import com.example.WEB_App.samples.DatabaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LoginService {

    public Optional<CustomUser> Login(String login, String password) {
        Optional<CustomUser> user;
        Repository rep =new Repository();
        user = rep.get(new LoginSpecification(login,password));
        return user;
    }

    public Optional<CustomUser> LoginDB(String login, String password) {
        Optional<CustomUser> user = Optional.empty();
        DatabaseHandler db = new DatabaseHandler();
        ResultSet userDB = db.loginUser(login,password);
        if(userDB!=null){
            try {
                while(userDB.next()){
                    String inform = userDB.getString("information");
                    user = Optional.of(new CustomUser(userDB.getLong("id"),
                            login,password,
                            inform));
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return user;
    }

}
