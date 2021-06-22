package com.example.topoftops.controller;

import com.example.topoftops.controller.command.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_COMMAND;

@WebServlet(name = "ajax_controller",urlPatterns = "/ajax_controller")
public class AjaxController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    private void processRequest(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        RequestHelper requestHelper = RequestHelper.getInstance();
        String action = request.getParameter(PARAM_NAME_COMMAND);
        AjaxCommand command = requestHelper.getAjaxCommand(action);
        command.execute(request);
        super.doPost(request,response);
    }
}
