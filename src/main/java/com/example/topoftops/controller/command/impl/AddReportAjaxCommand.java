package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.AjaxCommand;
import com.example.topoftops.entity.CustomUser;
import com.example.topoftops.entity.Report;
import com.example.topoftops.entity.Top;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.ReportService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import static com.example.topoftops.controller.command.RequestParam.*;

public class AddReportAjaxCommand implements AjaxCommand {
    private static final Logger logger = LogManager.getLogger();
    private ReportService reportService;

    public AddReportAjaxCommand(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public void execute(HttpServletRequest request) {
        String topid = request.getParameter(PARAM_NAME_TOP);
        CustomUser user = (CustomUser) request.getSession().getAttribute("user");
        String desc = request.getParameter(PARAM_NAME_DESCRIPTION);
        Report report = new Report(Long.parseLong(topid),user.getId(),desc);
        try {
            reportService.createReport(report);
        }catch (ServiceException e) {
            logger.log(Level.ERROR,e);
            request.setAttribute("error_message","CANT CREATE REPORT, TRY LATER");
        }
    }
}
