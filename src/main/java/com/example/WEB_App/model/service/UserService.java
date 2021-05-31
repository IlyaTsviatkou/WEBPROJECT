package com.example.WEB_App.model.service;

import com.example.WEB_App.entity.CustomUser;
import com.example.WEB_App.exception.DaoException;
import com.example.WEB_App.exception.ServiceException;
import com.example.WEB_App.model.dao.impl.UserDaoImpl;
import com.example.WEB_App.util.Encryptor;
import com.example.WEB_App.util.MailSender;
import com.example.WEB_App.validation.Validation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.MessagingException;
import java.sql.SQLException;

public class UserService {
    public static Logger logger = LogManager.getLogger();
    private static final String INFO_MESSAGE_SUBJECT_CONFIRMATION = "CONFIRM YOUR ACCOUNT";
    UserDaoImpl dao = new UserDaoImpl();

    public void sendMessage(String email, String message) {
        if(Validation.isValidEmail(email)) {
            MailSender.send(email, INFO_MESSAGE_SUBJECT_CONFIRMATION,
                    message);
        } else {
            //todo ERROR message
        }
    }

    public boolean register(CustomUser user) throws SQLException, ClassNotFoundException {
        boolean result = false;
        if(Validation.isValidPassword(user.getPassword()) && Validation.isValidLogin(user.getLogin())) {
            String encryptedPass = Encryptor.encrypt(user.getPassword());
            user.setPassword(encryptedPass);
            try {
                dao.register(user);
                result = true;
            } catch (Exception e) {
                logger.log(Level.WARN,e.getMessage());
            }
        }
        return result;
    }

    public boolean activate(String userId){
        long id = Long.valueOf(Encryptor.decryptC(userId));
        boolean result = dao.updateStatus(id,1,0);
        return result;
    }
}
