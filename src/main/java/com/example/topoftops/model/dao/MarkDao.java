package com.example.topoftops.model.dao;

import com.example.topoftops.entity.Mark;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.exception.ServiceException;

public interface MarkDao {
    boolean create(Mark mark) throws DaoException;
    boolean isExist(Mark mark) throws DaoException;
}
