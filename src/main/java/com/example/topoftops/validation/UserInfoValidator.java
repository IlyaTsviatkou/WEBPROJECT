package com.example.topoftops.validation;

public class UserInfoValidator {

    private static final String REGEXP_VALID_LOGIN = "^(?=[A-Za-z])[A-Za-z\\d\\_]{5,}$";
    private static final String REGEXP_VALID_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    private static final String REGEXP_VALID_EMAIL = "^[\\w-\\+]+(\\.[\\w-]+)*@[a-zA-Z\\d-]+(\\.[a-zA-Z\\d]+)*(\\.[a-zA-Z]{2,})$";

    public static boolean isValidLogin(String login) {
        boolean isValidLogin = login.matches(REGEXP_VALID_LOGIN);
        return isValidLogin;
    }

    public static boolean isValidPassword(String pass) {
        boolean isValidPassword = pass.matches(REGEXP_VALID_PASSWORD);
        return isValidPassword;
    }

    public static boolean isValidEmail(String email) {
        boolean isValidEmail = email.matches(REGEXP_VALID_EMAIL);
        return isValidEmail;
    }


}
