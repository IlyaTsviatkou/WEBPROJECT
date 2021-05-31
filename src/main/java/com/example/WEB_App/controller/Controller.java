package com.example.WEB_App.controller;



import com.example.WEB_App.controller.command.Command;
import com.example.WEB_App.controller.command.RequestHelper;
import com.example.WEB_App.controller.command.Router;
import com.example.WEB_App.resource.ConfigurationManager;
import com.example.WEB_App.resource.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "controller",urlPatterns = "/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private static final String PARAM_NAME_COMMAND = "command";

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
        String page = null;
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
                page = ConfigurationManager.getProperty("path.page.index");
                response.sendRedirect(request.getContextPath() + page);
                break;
        }
//        if (page != null) {
//            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
//            dispatcher.forward(request, response);
//        } else {
//            page = ConfigurationManager.getProperty("path.page.index");
//            request.getSession().setAttribute("nullPage", MessageManager.getProperty("message.nullpage"));
//            response.sendRedirect(request.getContextPath() + page);
//        }
    }

    public void destroy() {
    }
}