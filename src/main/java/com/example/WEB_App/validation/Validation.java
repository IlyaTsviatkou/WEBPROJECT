package com.example.WEB_App.validation;

public class Validation {

    private static final String REGEXP_VALID_LOGIN = "^(?=[A-Za-z])[A-Za-z\\d\\_]{5,}$";
    private static final String REGEXP_VALID_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    public static boolean isValidLogin(String login) {
        boolean isValidLogin = login.matches(REGEXP_VALID_LOGIN);
        return isValidLogin;
    }

    public static boolean isValidPassword(String pass) {
        boolean isValidPassword = pass.matches(REGEXP_VALID_PASSWORD);
        return isValidPassword;
    }
}
