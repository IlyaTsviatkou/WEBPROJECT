package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.*;
import com.example.topoftops.entity.User;
import com.example.topoftops.entity.Role;
import com.example.topoftops.entity.Top;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.ItemService;
import com.example.topoftops.model.service.ReportService;
import com.example.topoftops.model.service.TopService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.topoftops.controller.command.RequestParam.PARAM_ERROR_MESSAGE;
import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_TOP;

/**
 * The command is responsible for delete top
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class DeleteTopCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private ReportService reportService;
    private TopService topService;
    private ItemService itemService;

    public DeleteTopCommand(ReportService reportService, TopService topService, ItemService itemService) {
        this.reportService = reportService;
        this.topService = topService;
        this.itemService = itemService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page = ConfigurationManager.getProperty(PagePath.ERROR);
        long topId = Long.parseLong(request.getParameter(PARAM_NAME_TOP));
        User user = (User) request.getSession().getAttribute("user");
        try {
            Top top = (Top) topService.findById(topId).get();
            if (user.getId() == top.getUser() || user.getRole() == Role.ADMIN.ordinal()) {
                itemService.deleteByTop(topId);
                topService.delete(topId);
                reportService.deleteReports(topId);
                page = ConfigurationManager.getProperty(PagePath.INDEX);
            } else {
                logger.log(Level.ERROR, "user tried delete stranger top");
                request.getSession().setAttribute(PARAM_ERROR_MESSAGE, "user tried delete stranger top");
            }
        } catch (ServiceException e) {
            logger.log(Level.WARN, "any problem with top deleting", e);
            request.getSession().setAttribute(PARAM_ERROR_MESSAGE, "any problem with top deleting");
        }
        router = new Router(page, Router.RouteType.REDIRECT);
        return router;
    }
}
