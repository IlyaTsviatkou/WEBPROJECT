package com.example.topoftops.controller;



import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.RequestHelper;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.resource.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_COMMAND;

@WebServlet(name = "controller",urlPatterns = "/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
    private void processRequest(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        RequestHelper requestHelper = RequestHelper.getInstance();
        String action = request.getParameter(PARAM_NAME_COMMAND);
        Command command = requestHelper.getCommand(action);
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
                logger.error("incorrect route type " + router.getRouteType());
                String page;
                page = ConfigurationManager.getProperty(PagePath.INDEX);
                response.sendRedirect(request.getContextPath() + page);
        }

    }

    public void destroy() {
    }
}