package com.example.topoftops.model.service;

import com.example.topoftops.entity.Top;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.dao.impl.TopDaoImpl;
import com.example.topoftops.validation.InputInfoValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TopService {
    private static final Logger logger = LogManager.getLogger();
    private TopDaoImpl dao = new TopDaoImpl();

    public void create(Top top) throws ServiceException {
        try {
            if (InputInfoValidator.isValidTitle(top.getTitle())
                    && InputInfoValidator.isValidDescription(top.getDescription())) {
                dao.create(top);
            } else {
                logger.log(Level.WARN, "invalid input");
                throw new ServiceException("invalid input");
            }
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
            throw new ServiceException(e);
        }

    }

    public Optional<Top> findByTitle(String title) throws ServiceException {
        Optional<Top> top;
        try {
            if (InputInfoValidator.isValidTitle(title)) {
                top = Optional.of(dao.findTopByTitle(title));
            } else {
                logger.log(Level.WARN, "invalid input");
                throw new ServiceException("invalid input");
            }
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
            throw new ServiceException(e);
        }
        return top;
    }

    public Optional<Top> findById(long id) throws ServiceException {
        Optional<Top> top = Optional.empty();
        try {
            top = Optional.of(dao.findTopByID(id));
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
            throw new ServiceException(e);
        }
        return top;
    }

    public void updateRating(int mark, long id) throws ServiceException {
        try {
            dao.updateRating(mark, id);
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
            throw new ServiceException(e);
        }
    }

    public void delete(long id) throws ServiceException {
        try {
            dao.delete(id);
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
            throw new ServiceException(e);
        }
    }

    public List<Top> findAllTops() throws ServiceException {
        List<Top> list;
        try {
            list = dao.findAllTops();
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
            throw new ServiceException(e);
        }
        return list;
    }

    public List<Top> findAllTopsByUser(long id) throws ServiceException {
        List<Top> list;
        try {
            list = dao.findAllTopsByUser(id);
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
            throw new ServiceException(e);
        }
        return list;
    }

    public List<Top> searchInTops(String data) throws ServiceException {
        List<Top> list;
        try {
            if (InputInfoValidator.isValidSearch(data)) {
                list = dao.searchInTops(data);
            } else {
                logger.log(Level.WARN, "invalid input");
                throw new ServiceException("invalid input");
            }
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
            throw new ServiceException(e);
        }
        return list;
    }

    public List<Top> searchInTopsWithRating(String data, int count) throws ServiceException {
        List<Top> list;
        try {
            if (InputInfoValidator.isValidSearch(data)) {
                if (count > 0) {
                    list = dao.searchInTopsWithRatingUp(data);
                } else {
                    list = dao.searchInTopsWithRatingDown(data);
                }
            } else {
                logger.log(Level.WARN, "invalid input");
                throw new ServiceException("invalid input");
            }
        } catch (DaoException e) {
            logger.log(Level.WARN, e);
            throw new ServiceException(e);
        }
        return list;
    }

}
