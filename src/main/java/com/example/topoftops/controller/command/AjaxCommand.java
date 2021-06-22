package com.example.topoftops.controller.command;

import javax.servlet.http.HttpServletRequest;

public interface AjaxCommand {
    void execute(HttpServletRequest request);
}
