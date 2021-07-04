package com.example.topoftops.controller.command;

import javax.servlet.http.HttpServletRequest;


/**
 * The interface for different commands
 *
 * @author Ilya Tsvetkov
 */
public interface Command {
    /**
     * Executes command
     *
     * @param request {@link HttpServletRequest}
     * @return {@link Router}
     */
    Router execute(HttpServletRequest request);
}
