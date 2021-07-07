package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.AjaxCommand;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.ItemService;
import com.example.topoftops.model.service.ReportService;
import com.example.topoftops.model.service.TopService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_TOP;

/**
 * The command is responsible for accepting report
 *
 * @author Ilya Tsvetkov
 * @see AjaxCommand
 */
public class AcceptReportAjaxCommand implements AjaxCommand {
    private static final Logger logger = LogManager.getLogger();
    private ReportService reportService;
    private TopService topService;
    private ItemService itemService;

    public AcceptReportAjaxCommand(ReportService reportService, TopService topService, ItemService itemService) {
        this.reportService = reportService;
        this.topService = topService;
        this.itemService = itemService;
    }

    @Override
    public void execute(HttpServletRequest request) {
        long topId = Long.parseLong(request.getParameter(PARAM_NAME_TOP));
        try {
            itemService.deleteByTop(topId);
            topService.delete(topId);
            reportService.deleteReports(topId);
        } catch (ServiceException e) {
            logger.log(Level.WARN, "couldnt accept report", e);
        }
    }
}
