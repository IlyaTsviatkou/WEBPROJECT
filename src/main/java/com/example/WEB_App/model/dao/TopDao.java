package com.example.WEB_App.model.dao;

import com.example.WEB_App.entity.Top;
import com.example.WEB_App.exception.DaoException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TopDao {
    void create(Top top) throws DaoException, SQLException;
    ArrayList<Top> findAllTops(long id) throws DaoException, SQLException;
    Top findTopByTitle(String title) throws DaoException, SQLException;
    Top findTopByID(long id) throws DaoException, SQLException;
}
