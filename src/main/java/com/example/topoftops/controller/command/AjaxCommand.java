package com.example.topoftops.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface for different ajax commands
 *
 * @author Ilya Tsvetkov
 */
public interface AjaxCommand {
    /**
     * Executes ajax command
     *
     * @param request {@link HttpServletRequest}
     */
    void execute(HttpServletRequest request);
}
