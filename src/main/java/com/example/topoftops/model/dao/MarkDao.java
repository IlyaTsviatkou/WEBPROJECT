package com.example.topoftops.model.dao;

import com.example.topoftops.entity.Mark;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.exception.ServiceException;

import java.sql.SQLException;

/**
 * The interface for working with database table marks
 *
 * @author Ilya Tsvetkov
 */
public interface MarkDao {
    /**
     * Delete by top id
     *
     * @param mark {@link Mark} mark
     * @return boolean if created return true otherwise false
     * @throws DaoException if {@link SQLException} occur
     */
    boolean create(Mark mark) throws DaoException;

    /**
     * Check if exist mark
     *
     * @param mark {@link Mark} mark
     * @return boolean if exist return true otherwise false
     * @throws DaoException if {@link SQLException} occur
     */
    boolean isExist(Mark mark) throws DaoException;
}
