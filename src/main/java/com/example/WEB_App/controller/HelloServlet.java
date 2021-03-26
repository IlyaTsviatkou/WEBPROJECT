package com.example.WEB_App.controller;



import com.example.WEB_App.command.Command;
import com.example.WEB_App.command.RequestHelper;
import com.example.WEB_App.resource.ConfigurationManager;
import com.example.WEB_App.resource.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(/*name = "helloServlet", value = "/hello-servlet" ,*/ urlPatterns = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private static final String PARAM_NAME_COMMAND = "command";
    private String message;

    public void init() {
        message = "LOGGER SUCCESSFULLY TESTED";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        logger.info("DID IT");
        logger.warn("OOPs");
        logger.error("ERROR");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("post: " + req.toString());
        processRequest(req, resp);
    }
    private void processRequest(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        String page = null;
        RequestHelper requestHelper = RequestHelper.getInstance();
        String action = request.getParameter(PARAM_NAME_COMMAND);
        Command command = requestHelper.getCommand(action);
        page = command.execute(request);
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            page = ConfigurationManager.getProperty("path.page.index");
            request.getSession().setAttribute("nullPage", MessageManager.getProperty("message.nullpage"));
            response.sendRedirect(request.getContextPath() + page);
        }
    }

    public void destroy() {
    }
}