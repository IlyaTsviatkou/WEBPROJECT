package com.example.topoftops.model.dao;

import com.example.topoftops.exception.ConnectionPoolException;
import com.example.topoftops.exception.DaoException;

import java.sql.SQLException;

/**
 * The root interface in the dao hierarchy
 *
 * @param <T> entity
 * @author Ilya Tsvetkov
 */
public interface BaseDao<T> {
    /**
     * Creates a new record in the corresponding database table
     *
     * @param entity all data to create
     * @throws DaoException if {@link ConnectionPoolException} or
     *                      {@link SQLException} occur
     */
    void create(T entity) throws DaoException;

    /**
     * Delete record from the corresponding database table
     *
     * @param id to delete
     * @throws DaoException if {@link ConnectionPoolException} or
     *                      {@link SQLException} occur
     */
    void delete(long id) throws DaoException;

}
