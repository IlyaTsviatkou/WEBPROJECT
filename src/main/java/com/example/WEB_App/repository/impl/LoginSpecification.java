package com.example.WEB_App.repository.impl;

import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.repository.Specification;

public class LoginSpecification implements Specification {
    private String login;
    private String password;

    public LoginSpecification(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean specify(CustomUser user) {
        boolean result = user.getLogin().equals(login) && user.getPassword().equals(password);
        return result;
    }
}
