package com.example.topoftops.entity;

import java.util.Objects;

public class CustomUser {
    private long id;
    private String login;
    private String password;
    private String email;
    private int role;
    private int status;
    private int rating;

    public CustomUser() {
    }

    public CustomUser(long id, String login, String password, String email, int role, int status) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public CustomUser(String login, String password, String email, int role, int status) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String information) {
        this.email = information;
    }

    @Override
    public boolean equals(Object o) {//fixme without Objects and add tostring
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomUser user = (CustomUser) o;
        return id == user.id && Objects.equals(login, user.login) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        int temp = 31;
        int result = (int) (temp * id)
                + temp * login.length()
                + temp * password.length()
                + temp * status;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
