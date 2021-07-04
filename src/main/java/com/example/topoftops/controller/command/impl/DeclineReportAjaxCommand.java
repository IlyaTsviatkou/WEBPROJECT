package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.AjaxCommand;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.ReportService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.topoftops.controller.command.RequestParam.*;

/**
 * The command is responsible for declining report
 *
 * @author Ilya Tsvetkov
 * @see AjaxCommand
 */
public class DeclineReportAjaxCommand implements AjaxCommand {
    private static final Logger logger = LogManager.getLogger();
    private ReportService reportService;

    public DeclineReportAjaxCommand(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public void execute(HttpServletRequest request) {
        long reportId = Long.parseLong(request.getParameter(PARAM_NAME_REPORT));
        try {
            reportService.deleteReportById(reportId);
        } catch (ServiceException e) {
            logger.log(Level.WARN, "couldnt delete report by top id", e);
        }
    }
}
