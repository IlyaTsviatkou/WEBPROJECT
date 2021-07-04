package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.ConfigurationManager;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.entity.Item;
import com.example.topoftops.entity.Top;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.TopService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_TOP;

/**
 * The command is responsible for going to tops page
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class ToTopsPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private TopService topService;

    public ToTopsPageCommand(TopService topService) {
        this.topService = topService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page;
        List<Top> tops;
        try {
            tops = topService.findAllTops();
            request.setAttribute("tops", tops);
            page = ConfigurationManager.getProperty(PagePath.TOPS);
        } catch (ServiceException e) {
            page = ConfigurationManager.getProperty(PagePath.INDEX);
            logger.log(Level.WARN, e);
        }
        router = new Router(page, Router.RouteType.FORWARD);
        return router;
    }
}
