package com.example.topoftops.controller;

import com.example.topoftops.controller.command.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.google.gson.*;

import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_COMMAND;

/**
 * AjaxController receive request from client (get or post)
 */
@WebServlet(name = "ajax_controller", urlPatterns = "/ajax_controller")
public class AjaxController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(request.getAttribute("data"));
        response.getWriter().print(json);
    }

    /**
     * Processes get and post requests
     *
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        RequestHelper requestHelper = RequestHelper.getInstance();
        String commandName = request.getParameter(PARAM_NAME_COMMAND);
        AjaxCommand command = requestHelper.getAjaxCommand(commandName);
        request.getSession().setAttribute(PARAM_NAME_COMMAND, commandName);
        command.execute(request);
    }
}
