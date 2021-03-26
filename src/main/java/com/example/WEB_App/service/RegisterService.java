package com.example.WEB_App.service;

import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.repository.Repository;
import com.example.WEB_App.repository.impl.LoginSpecification;
import com.example.WEB_App.samples.DatabaseHandler;

import java.sql.SQLException;
import java.util.Optional;

public class RegisterService {

    public boolean register(CustomUser user) throws SQLException, ClassNotFoundException {
        boolean result = true;
        DatabaseHandler db = new DatabaseHandler();
        try {
            db.singUpUser(user);
        }catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}
