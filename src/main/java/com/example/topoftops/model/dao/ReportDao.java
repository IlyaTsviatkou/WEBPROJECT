package com.example.topoftops.model.dao;

import com.example.topoftops.entity.Report;
import com.example.topoftops.exception.DaoException;

import java.util.List;

public interface ReportDao {
    void create(Report report) throws DaoException;
    void delete(long id) throws DaoException ;
    List<Report> findAllReports() throws DaoException  ;

}
