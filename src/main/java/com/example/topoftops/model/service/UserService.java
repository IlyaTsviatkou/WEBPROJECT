package com.example.topoftops.model.service;

import com.example.topoftops.entity.CustomUser;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.dao.impl.UserDaoImpl;
import com.example.topoftops.util.Encryptor;
import com.example.topoftops.util.MailSender;
import com.example.topoftops.validation.UserInfoValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final String INFO_MESSAGE_SUBJECT_CONFIRMATION = "CONFIRM YOUR ACCOUNT";
    private UserDaoImpl dao = new UserDaoImpl();

    public boolean sendMessage(String email, String message) {
        boolean result = false;
        if(UserInfoValidator.isValidEmail(email)) {
            MailSender.send(email, INFO_MESSAGE_SUBJECT_CONFIRMATION,
                    message);
            result = true;
        }
        return result;
    }

    public boolean register(CustomUser user) throws ServiceException {
        boolean result = false;
        if(UserInfoValidator.isValidPassword(user.getPassword()) && UserInfoValidator.isValidLogin(user.getLogin())) {
            String encryptedPass = Encryptor.encrypt(user.getPassword());
            user.setPassword(encryptedPass);
            try {
                result = dao.register(user);
            } catch (Exception e) {
                logger.log(Level.WARN,e.getMessage());
                throw new ServiceException(e);
            }
        }
        return result;
    }

    public boolean activate(String userId) throws ServiceException {
        String login = Encryptor.decryptC(userId);
        boolean result = false;
        try {
            result = dao.updateStatus(login,1,0);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }
}
