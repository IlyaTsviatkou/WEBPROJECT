package com.example.topoftops.model.dao;

import com.example.topoftops.entity.User;
import com.example.topoftops.exception.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * The interface for working with database table users
 *
 * @author Ilya Tsvetkov
 */
public interface UserDao {
    /**
     * Login
     *
     * @param login {@link String} login
     * @param pass  {@link String} pass
     * @return user
     * @throws DaoException if {@link SQLException} occur
     */
    Optional<User> login(String login, String pass) throws DaoException;

    /**
     * Register
     *
     * @param user {@link User} user
     * @throws DaoException if {@link SQLException} occur
     */
    void register(User user) throws DaoException;

    /**
     * Find all users by name
     *
     * @param userName {@link String} username
     * @return list of users
     * @throws DaoException if {@link SQLException} occur
     */
    List<User> findUsersByName(String userName) throws DaoException;

    /**
     * Update status with check
     *
     * @param login      {@link String} login
     * @param statusTo   {@link Integer} status1
     * @param statusFrom {@link Integer} status2
     * @return boolean true if updated otherwise false
     * @throws DaoException if {@link SQLException} occur
     */
    boolean updateStatus(String login, int statusTo, int statusFrom) throws DaoException;

    /**
     * update status by id
     *
     * @param userId   {@link String} userId
     * @param statusTo {@link Integer} status
     * @throws DaoException if {@link SQLException} occur
     */
    void updateStatusById(String userId, int statusTo) throws DaoException;


}
