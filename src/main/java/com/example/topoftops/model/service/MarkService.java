package com.example.topoftops.model.service;


import com.example.topoftops.entity.Mark;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.dao.impl.MarkDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MarkService {
    private static final Logger logger = LogManager.getLogger();
    private MarkDaoImpl markDao = new MarkDaoImpl();
    public boolean create(Mark mark) throws ServiceException {
        boolean result;
        try{
            result = markDao.create(mark);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }
    public boolean isExist(Mark mark) throws ServiceException {
        boolean result;
        try{
            result = markDao.isExist(mark);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }
}
