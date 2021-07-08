package com.example.topoftops.controller;


import com.example.topoftops.controller.command.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_COMMAND;

/**
 * Controller receive request from client (get or post)
 *
 * @author Ilya Tsvetkov
 * @see HttpServlet
 */
@WebServlet(name = "controller", urlPatterns = "/controller")
@MultipartConfig
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private String ATTRIBUTE_ERROR_MESSAGE = "error_message";

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestHelper requestHelper = RequestHelper.getInstance();

        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        processRequest(req, resp);
    }

    /**
     * Processes get and post requests
     *
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @throws ServletException
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestHelper requestHelper = RequestHelper.getInstance();
        String commandName = request.getParameter(PARAM_NAME_COMMAND);
        Command command = requestHelper.getCommand(commandName);
        request.getSession().setAttribute(PARAM_NAME_COMMAND, commandName);
        Router router = command.execute(request);
        switch (router.getRouteType()) {
            case REDIRECT:
                response.sendRedirect(request.getContextPath() + router.getPagePath());
                break;
            case FORWARD:
                RequestDispatcher dispatcher = request.getRequestDispatcher(router.getPagePath());
                dispatcher.forward(request, response);
                break;
            default:
                logger.log(Level.ERROR, "incorrect route type " + router.getRouteType());
                String page;
                page = ConfigurationManager.getProperty(PagePath.INDEX);
                response.sendRedirect(request.getContextPath() + page);
        }

    }

    public void destroy() {
    }
}