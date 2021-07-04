package com.example.topoftops.validation;

/**
 * Validator to validate user info
 *
 * @author Ilya Tsvetkov
 */
public class UserInfoValidator {

    private static final String REGEXP_VALID_LOGIN = "^(?=[A-Za-z])[A-Za-z\\d\\_]{5,15}$";
    private static final String REGEXP_VALID_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$";
    private static final String REGEXP_VALID_EMAIL = "^[\\w-\\+]+(\\.[\\w-]+)*@[a-zA-Z\\d-]+(\\.[a-zA-Z\\d]+)*(\\.[a-zA-Z]{2,30})$";

    private UserInfoValidator() {
    }

    /**
     * validation login
     *
     * @return boolean if no valid true otherwise false
     */
    public static boolean isValidLogin(String login) {
        boolean isValidLogin = false;
        if (login != null) {
            isValidLogin = login.matches(REGEXP_VALID_LOGIN);
        }
        return isValidLogin;
    }

    /**
     * validation password
     *
     * @return boolean if no valid true otherwise false
     */
    public static boolean isValidPassword(String pass) {
        boolean isValidPassword = false;
        if (pass != null) {
            isValidPassword = pass.matches(REGEXP_VALID_PASSWORD);
        }
        return isValidPassword;
    }

    /**
     * validation email
     *
     * @return boolean if no valid true otherwise false
     */
    public static boolean isValidEmail(String email) {
        boolean isValidEmail = false;
        if (email != null) {
            isValidEmail = email.matches(REGEXP_VALID_EMAIL);
        }
        return isValidEmail;
    }


}
