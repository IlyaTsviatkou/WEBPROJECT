package com.example.topoftops.model.dao;

import com.example.topoftops.entity.Report;
import com.example.topoftops.exception.DaoException;

import java.sql.SQLException;
import java.util.List;

/**
 * The interface for working with database table reports
 *
 * @author Ilya Tsvetkov
 * @see BaseDao
 */
public interface ReportDao extends BaseDao<Report> {
    /**
     * Delete by top id
     *
     * @param top {@link Long} top id
     * @throws DaoException if {@link SQLException} occur
     */
    void deleteByTop(long top) throws DaoException;

    /**
     * Find all reports
     *
     * @throws DaoException if {@link SQLException} occur
     */
    List<Report> findAllReports() throws DaoException;

}
