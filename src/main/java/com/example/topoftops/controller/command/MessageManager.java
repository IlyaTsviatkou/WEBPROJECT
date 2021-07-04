package com.example.topoftops.controller.command;

import java.util.ResourceBundle;

/**
 * Manager of strings in messages.property
 *
 * @author Ilya Tsvetkov
 */
public class MessageManager {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    private MessageManager() {
    }

    /**
     * Get message and return it
     *
     * @param key
     * @return message
     */
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
