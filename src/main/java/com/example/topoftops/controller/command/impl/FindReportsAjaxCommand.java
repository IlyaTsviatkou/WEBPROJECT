package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.AjaxCommand;
import com.example.topoftops.entity.Report;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.ReportService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * The command is responsible for find report
 *
 * @author Ilya Tsvetkov
 * @see AjaxCommand
 */
public class FindReportsAjaxCommand implements AjaxCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_DATA = "data";
    private ReportService reportService;

    public FindReportsAjaxCommand(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public void execute(HttpServletRequest request) {
        List<Report> list = new ArrayList<>();
        try {
            list = reportService.findAllReports();
        } catch (ServiceException e) {
            logger.log(Level.WARN, e);
        }
        request.setAttribute(ATTRIBUTE_DATA, list);
    }
}
