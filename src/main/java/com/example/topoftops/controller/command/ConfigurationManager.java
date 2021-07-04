package com.example.topoftops.controller.command;

import java.util.ResourceBundle;

/**
 * Manager of strings in config.property
 *
 * @author Ilya Tsvetkov
 */
public class ConfigurationManager {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

    private ConfigurationManager() {
    }

    /**
     * Get path of page and return it
     *
     * @param key
     * @return path of page
     */
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
