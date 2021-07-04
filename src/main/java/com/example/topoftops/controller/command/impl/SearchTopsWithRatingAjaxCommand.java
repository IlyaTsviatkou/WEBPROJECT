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

import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_SEARCH_RATING;
import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_SEARCH_STRING;

/**
 * The command is responsible for search top  with rating
 *
 * @author Ilya Tsvetkov
 * @see AjaxCommand
 */
public class SearchTopsWithRatingAjaxCommand implements AjaxCommand {
    private static final Logger logger = LogManager.getLogger();
    private TopService topService;

    public SearchTopsWithRatingAjaxCommand(TopService topService) {
        this.topService = topService;
    }

    @Override
    public void execute(HttpServletRequest request) {
        List<Top> tops;
        try {
            String data = request.getParameter(PARAM_NAME_SEARCH_STRING);
            int rating = Integer.parseInt(request.getParameter(PARAM_NAME_SEARCH_RATING));
            tops = topService.searchInTopsWithRating(data, rating);
            request.setAttribute("data", tops);
        } catch (ServiceException e) {
            logger.log(Level.WARN, e);
        }
    }
}
