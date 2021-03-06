package com.example.topoftops.model.service;

import com.example.topoftops.entity.User;
import com.example.topoftops.exception.DaoException;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.dao.impl.UserDaoImpl;
import com.example.topoftops.util.Encryptor;
import com.example.topoftops.util.MailSender;
import com.example.topoftops.validation.InputInfoValidator;
import com.example.topoftops.validation.UserInfoValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final String INFO_MESSAGE_SUBJECT_CONFIRMATION = "CONFIRM YOUR ACCOUNT";
    private UserDaoImpl dao = new UserDaoImpl();

    /**
     * authorisation of user
     * @param login
     * @param password
     * @return
     * @throws ServiceException
     */
    public Optional<User> login(String login, String password) throws ServiceException {
        Optional<User> user = Optional.empty();
        if(UserInfoValidator.isValidPassword(password) && UserInfoValidator.isValidLogin(login)) {
            String encryptedPass = Encryptor.encrypt(password);
            try {
                user = dao.login(login, encryptedPass);
            } catch (DaoException e) {
                logger.log(Level.WARN,e.getMessage());
                throw new ServiceException(e);
            }
        }
        return user;
    }

    /**
     * send message to email
     * @param email
     * @param message
     * @return
     */
    public boolean sendMessage(String email, String message) {
        boolean result = false;
        if(UserInfoValidator.isValidEmail(email)) {
            MailSender.send(email, INFO_MESSAGE_SUBJECT_CONFIRMATION,
                    message);
            result = true;
        }
        return result;
    }

    /**
     * find users by nickname
     * @param userName
     * @return
     * @throws ServiceException
     */
    public List<User> findUsersByName(String userName) throws ServiceException {
        try {
            if(InputInfoValidator.isValidSearch(userName)) {
                return dao.findUsersByName(userName);
            } else {
                logger.log(Level.WARN,"invalid input");
                throw new ServiceException("invalid input");
            }

        } catch (DaoException e) {
            logger.log(Level.WARN,e);
            throw new ServiceException(e);
        }
    }

    /**
     * register user
     * @param user
     * @return
     * @throws ServiceException
     */
    public boolean register(User user) throws ServiceException {
        boolean result = false;
        if(UserInfoValidator.isValidPassword(user.getPassword()) && UserInfoValidator.isValidLogin(user.getLogin())
                && UserInfoValidator.isValidEmail(user.getEmail())) {
            String encryptedPass = Encryptor.encrypt(user.getPassword());
            user.setPassword(encryptedPass);
            try {
                dao.register(user);
                result = true;
            } catch (Exception e) {
                logger.log(Level.WARN,e.getMessage());
                throw new ServiceException(e);
            }
        }
        return result;
    }

    /**
     * activate user by message from email
     * @param userId
     * @return
     * @throws ServiceException
     */
    public boolean activate(String userId) throws ServiceException {
        String login = Encryptor.decryptC(userId);
        boolean result = false;
        try {
            result = dao.updateStatus(login,1,0);
        } catch (DaoException e) {
            logger.log(Level.WARN,e);
            throw new ServiceException(e);
        }
        return result;
    }

    /**
     * block user by id
     * @param userId
     * @throws ServiceException
     */
    public void block(String userId) throws ServiceException {
        try {
            dao.updateStatusById(userId,2);
        } catch (DaoException e) {
            logger.log(Level.WARN,e);
            throw new ServiceException(e);
        }
    }

    /**
     * unblock user by id
     * @param userId
     * @throws ServiceException
     */
    public void unblock(String userId) throws ServiceException {
        try {
            dao.updateStatusById(userId,1);
        } catch (DaoException e) {
            logger.log(Level.WARN,e);
            throw new ServiceException(e);
        }
    }

    /**
     * delete user by id
     * @param userId
     * @throws ServiceException
     */
    public void delete(String userId) throws ServiceException {
        try {
            dao.updateStatusById(userId,3);
        } catch (DaoException e) {
            logger.log(Level.WARN,e);
            throw new ServiceException(e);
        }
    }
}
