package com.example.WEB_App.model.service;

import com.example.WEB_App.entity.Top;
import com.example.WEB_App.model.dao.impl.TopDaoImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class TopService {
    public static Logger logger = LogManager.getLogger();
    TopDaoImpl dao = new TopDaoImpl();

    public Optional<Top> findByTitle(String title) {
        Optional<Top> top = Optional.empty();
        try {
            top = Optional.of(dao.findTopByTitle(title));
        } catch (Exception e) {
            logger.log(Level.WARN,e.getMessage());
        }
        return top;
    }
    public Optional<Top> findById(long id) {
        Optional<Top> top = Optional.empty();
        try {
            top = Optional.of(dao.findTopByID(id));
        } catch (Exception e) {
            logger.log(Level.WARN,e.getMessage());
        }
        return top;
    }
}
