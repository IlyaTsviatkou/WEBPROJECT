package com.example.WEB_App.entity;

import java.util.Objects;

public class CustomUser {
    private long id;
    private String login;
    private String password;
    private String information;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public CustomUser(long id, String login, String password , String information) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.information = information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomUser user = (CustomUser) o;
        return id == user.id && Objects.equals(login, user.login) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        int temp =31;
        int result = (int) (temp * id)
                + temp * login.length()
                + temp * password.length();
        return result;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
