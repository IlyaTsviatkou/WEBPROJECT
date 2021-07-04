package com.example.topoftops.model.dao.impl;

import com.example.topoftops.entity.Item;
import com.example.topoftops.entity.Report;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.model.dao.ReportDao;
import com.example.topoftops.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.topoftops.model.dao.ColumnName.*;
import static com.example.topoftops.model.dao.ColumnName.COLUMN_USER;

public class ReportDaoImpl implements ReportDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_INSERT_CREATE = "INSERT INTO " + REPORT_TABLE + "(" +
            COLUMN_TOP + "," + COLUMN_USER + "," +
            COLUMN_DESCRIPTION + ")" +
            " SELECT ?,?,? FROM DUAL WHERE " +
            " ((SELECT COUNT(id) as count FROM reports WHERE " +
            COLUMN_USER + "=? AND " + COLUMN_TOP + " =? ) <3 )";
    private static final String SQL_SELECT_ALL_REPORTS = "SELECT " + COLUMN_ID + ", " +
            COLUMN_TOP + ", " +
            COLUMN_DESCRIPTION + ", " +
            COLUMN_USER + " FROM " +
            REPORT_TABLE;
    private static final String SQL_DELETE_BY_TOP = "DELETE FROM " + REPORT_TABLE +
            " where " + COLUMN_TOP + " =?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM " + REPORT_TABLE +
            " where " + COLUMN_ID + " =?";

    @Override
    public void create(Report report) throws DaoException {
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_CREATE)) {
            preparedStatement.setLong(1, report.getTop());
            preparedStatement.setLong(2, report.getUser());
            preparedStatement.setString(3, report.getDescription());
            preparedStatement.setLong(4, report.getUser());
            preparedStatement.setLong(5, report.getTop());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteByTop(long top) throws DaoException {
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_TOP)) {
            preparedStatement.setLong(1, top);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(long id) throws DaoException {
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Report> findAllReports() throws DaoException {
        ArrayList<Report> reports = new ArrayList<>();
        try (Connection connection = CustomConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_REPORTS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Report report = new Report();
                report.setId(resultSet.getLong(COLUMN_ID));
                report.setDescription(resultSet.getString(COLUMN_DESCRIPTION));
                report.setTop(resultSet.getLong(COLUMN_TOP));
                report.setUser(resultSet.getLong(COLUMN_USER));
                reports.add(report);
            }
        } catch (SQLException e) {
            logger.log(Level.WARN, e.getMessage());
            throw new DaoException(e);
        }
        return reports;
    }
}
