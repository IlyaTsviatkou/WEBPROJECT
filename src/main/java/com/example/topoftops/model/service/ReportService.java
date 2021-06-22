package com.example.topoftops.model.service;

import com.example.topoftops.entity.Report;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.dao.impl.ReportDaoImpl;

public class ReportService {
    private ReportDaoImpl reportDao = new ReportDaoImpl();
    public void createReport(Report report) throws ServiceException {
        try {
            reportDao.create(report);
        }catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
