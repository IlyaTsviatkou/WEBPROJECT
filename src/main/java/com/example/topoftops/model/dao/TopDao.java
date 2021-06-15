package com.example.topoftops.model.dao;

import com.example.topoftops.entity.Top;
import com.example.topoftops.exception.DaoException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TopDao {
    void create(Top top) throws DaoException;
    ArrayList<Top> findAllTops(long id) throws DaoException;
    Top findTopByTitle(String title) throws DaoException;
    Top findTopByID(long id) throws DaoException;
}
