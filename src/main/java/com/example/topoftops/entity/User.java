package com.example.topoftops.entity;

import java.util.Objects;

/**
 * Describes the entity User
 *
 * @author Ilya Tsvetkov
 */
public class User {
    private long id;
    private String login;
    private String password;
    private String email;
    private int role;
    private int status;
    private int rating;

    /**
     * Constructs a new User
     */
    public User() {
    }

    /**
     * Constructs a new User with params
     *
     * @param id       {@link Long} user id
     * @param login    {@link String} top id
     * @param password {@link String} password
     * @param email    {@link String} email
     * @param role     {@link Integer} role
     * @param status   {@link Integer} status
     */
    public User(long id, String login, String password, String email, int role, int status) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    /**
     * Constructs a new User with params without id
     *
     * @param login    {@link String} top id
     * @param password {@link String} password
     * @param email    {@link String} email
     * @param role     {@link Integer} role
     * @param status   {@link Integer} status
     */
    public User(String login, String password, String email, int role, int status) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (role != user.role) return false;
        if (status != user.status) return false;
        if (rating != user.rating) return false;
        if (!login.equals(user.login)) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        int temp = 31;
        int result = (int) (temp * id)
                + temp * login.length()
                + temp * password.length()
                + temp * email.length()
                + temp * status
                + temp * rating
                + temp * role;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", role=").append(role);
        sb.append(", status=").append(status);
        sb.append(", rating=").append(rating);
        sb.append('}');
        return sb.toString();
    }
}
