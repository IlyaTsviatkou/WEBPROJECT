package com.example.topoftops.model.service;

import com.example.topoftops.entity.Top;
import com.example.topoftops.model.dao.impl.TopDaoImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateTopService {
    private static final Logger logger = LogManager.getLogger();
    private TopDaoImpl dao = new TopDaoImpl();

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
