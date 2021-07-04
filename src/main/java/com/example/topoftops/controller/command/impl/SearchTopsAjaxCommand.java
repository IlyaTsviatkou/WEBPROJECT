package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.AjaxCommand;
import com.example.topoftops.entity.Top;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.TopService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_SEARCH_STRING;

/**
 * The command is responsible for search top
 *
 * @author Ilya Tsvetkov
 * @see AjaxCommand
 */
public class SearchTopsAjaxCommand implements AjaxCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_DATA = "data";
    private TopService topService;

    public SearchTopsAjaxCommand(TopService topService) {
        this.topService = topService;
    }

    @Override
    public void execute(HttpServletRequest request) {
        List<Top> tops;
        try {
            String data = request.getParameter(PARAM_NAME_SEARCH_STRING);
            tops = topService.searchInTops(data);
            request.setAttribute(ATTRIBUTE_DATA, tops);
        } catch (ServiceException e) {
            logger.log(Level.WARN, e);
        }
    }
}
