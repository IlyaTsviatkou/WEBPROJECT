package com.example.topoftops.model.dao;

import com.example.topoftops.entity.Mark;
import com.example.topoftops.entity.Top;
import com.example.topoftops.exception.DaoException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The interface for working with database table tops
 *
 * @author Ilya Tsvetkov
 * @see BaseDao
 */
public interface TopDao extends BaseDao<Top> {
    /**
     * Find all tops
     *
     * @return list of tops
     * @throws DaoException if {@link SQLException} occur
     */
    List<Top> findAllTops() throws DaoException;

    /**
     * Find all tops by user id
     *
     * @param id {@link Long} user id
     * @throws DaoException if {@link SQLException} occur
     */
    List<Top> findAllTopsByUser(long id) throws DaoException;

    /**
     * Find top by title
     *
     * @param title {@link String} title
     * @throws DaoException if {@link SQLException} occur
     */
    Top findTopByTitle(String title) throws DaoException;

    /**
     * Find top by id
     *
     * @param id {@link Long} id
     * @throws DaoException if {@link SQLException} occur
     */
    Top findTopByID(long id) throws DaoException;

    /**
     * Update rating
     *
     * @param id   {@link Long}  id
     * @param mark {@link Integer} mark
     * @throws DaoException if {@link SQLException} occur
     */
    void updateRating(int mark, long id) throws DaoException;

    /**
     * Search tops by data
     *
     * @param data {@link String} data
     * @return list of tops
     * @throws DaoException if {@link SQLException} occur
     */
    List<Top> searchInTops(String data) throws DaoException;

    /**
     * Search tops by data with rating up
     *
     * @param data {@link String} data
     * @return list of tops
     * @throws DaoException if {@link SQLException} occur
     */
    List<Top> searchInTopsWithRatingUp(String data) throws DaoException;

    /**
     * Search tops by data with rating down
     *
     * @param data {@link String} data
     * @return list of tops
     * @throws DaoException if {@link SQLException} occur
     */
    List<Top> searchInTopsWithRatingDown(String data) throws DaoException;
}
