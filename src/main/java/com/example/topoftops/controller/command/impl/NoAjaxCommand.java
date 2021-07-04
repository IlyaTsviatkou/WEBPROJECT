package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.AjaxCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The command is responsible for invalid command by user
 *
 * @author Ilya Tsvetkov
 * @see AjaxCommand
 */
public class NoAjaxCommand implements AjaxCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void execute(HttpServletRequest request) {
        logger.log(Level.WARN, "tried get non-existent command ");
    }
}
