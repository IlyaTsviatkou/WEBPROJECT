package com.example.topoftops.model.service;

import com.example.topoftops.entity.Top;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.dao.impl.TopDaoImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class TopService {
    private static final Logger logger = LogManager.getLogger();
    private TopDaoImpl dao = new TopDaoImpl();

    public Optional<Top> findByTitle(String title) throws ServiceException {
        Optional<Top> top = Optional.empty();
        try {
            top = Optional.of(dao.findTopByTitle(title));
        } catch (Exception e) {
            logger.log(Level.WARN,e.getMessage());
            throw new ServiceException(e);
        }
        return top;
    }
    public Optional<Top> findById(long id) throws ServiceException {
        Optional<Top> top = Optional.empty();
        try {
            top = Optional.of(dao.findTopByID(id));
        } catch (Exception e) {
            logger.log(Level.WARN,e.getMessage());
            throw new ServiceException(e);
        }
        return top;
    }
    public void updateRating(int mark, long id) throws ServiceException {
        try {
            dao.updateRating(mark, id);
        } catch (Exception e) {
            logger.log(Level.WARN,e.getMessage());
            throw new ServiceException(e);
        }
    }

}
