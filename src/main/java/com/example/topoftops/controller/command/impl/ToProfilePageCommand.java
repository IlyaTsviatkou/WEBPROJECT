package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.ConfigurationManager;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.entity.User;
import com.example.topoftops.entity.Top;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.TopService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The command is responsible for going to profile page
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class ToProfilePageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private TopService topService;

    public ToProfilePageCommand(TopService topService) {
        this.topService = topService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page;
        List<Top> tops;
        try {
            User user = (User) request.getSession().getAttribute("user");
            tops = topService.findAllTopsByUser(user.getId());
            request.setAttribute("tops", tops);
            page = ConfigurationManager.getProperty(PagePath.PROFILE);
        } catch (ServiceException e) {
            logger.log(Level.WARN, "couldnt find tops with user id", e);
            page = ConfigurationManager.getProperty(PagePath.INDEX);
        }
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
