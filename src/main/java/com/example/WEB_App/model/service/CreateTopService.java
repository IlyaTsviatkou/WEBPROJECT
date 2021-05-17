package com.example.WEB_App.model.service;

import com.example.WEB_App.entity.Top;
import com.example.WEB_App.model.dao.impl.TopDaoImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateTopService {
    public static Logger logger = LogManager.getLogger();
    TopDaoImpl dao = new TopDaoImpl();

    public boolean create(Top top) {
        boolean result = true;
        try {
            dao.create(top);
        } catch (Exception e) {
            logger.log(Level.WARN,e.getMessage());
            result = false;
        }

        return result;
    }

}
