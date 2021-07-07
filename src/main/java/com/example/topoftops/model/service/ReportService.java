package com.example.topoftops.model.service;

import com.example.topoftops.entity.Report;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.dao.impl.ReportDaoImpl;
import com.example.topoftops.validation.InputInfoValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ReportService {
    private static final Logger logger = LogManager.getLogger();
    private ReportDaoImpl reportDao = new ReportDaoImpl();

    /**
     * create report
     * @param report
     * @throws ServiceException
     */
    public void createReport(Report report) throws ServiceException {
        try {
            if (InputInfoValidator.isValidDescription(report.getDescription())) {
                reportDao.create(report);
            } else {
                logger.log(Level.WARN, "invalid input");
                throw new ServiceException("invalid input");
            }
        } catch (DaoException e) {
            logger.log(Level.WARN, e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * delete reports by top id
     * @param top
     * @throws ServiceException
     */
    public void deleteReports(long top) throws ServiceException {
        try {
            reportDao.deleteByTop(top);
        } catch (DaoException e) {
            logger.log(Level.WARN, e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * delete report by id
     * @param id
     * @throws ServiceException
     */
    public void deleteReportById(long id) throws ServiceException {
        try {
            reportDao.delete(id);
        } catch (DaoException e) {
            logger.log(Level.WARN, e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * find all reports
     * @return
     * @throws ServiceException
     */
    public List<Report> findAllReports() throws ServiceException {
        List<Report> list;
        try {
            list = reportDao.findAllReports();
        } catch (DaoException e) {
            logger.log(Level.WARN, e.getMessage());
            throw new ServiceException(e);
        }
        return list;
    }
}
