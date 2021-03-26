package com.example.WEB_App.resource;

import java.util.ResourceBundle;

public class MessageManager {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    private MessageManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
