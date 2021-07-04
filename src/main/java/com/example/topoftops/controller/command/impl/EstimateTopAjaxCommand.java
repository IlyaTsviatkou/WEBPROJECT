package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.AjaxCommand;
import com.example.topoftops.entity.User;
import com.example.topoftops.entity.Mark;
import com.example.topoftops.exception.ServiceException;
import com.example.topoftops.model.service.MarkService;
import com.example.topoftops.model.service.TopService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_TOP;

/**
 * The command is responsible for estimate top
 *
 * @author Ilya Tsvetkov
 * @see AjaxCommand
 */
public class EstimateTopAjaxCommand implements AjaxCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_USER = "user";
    private int markCount;
    private MarkService markService;
    private TopService topService;

    public EstimateTopAjaxCommand(MarkService markService, TopService topService, int mark) {
        this.markService = markService;
        this.topService = topService;
        this.markCount = mark;
    }

    @Override
    public void execute(HttpServletRequest request) {
        String topid = request.getParameter(PARAM_NAME_TOP);
        User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        Mark mark = new Mark(Long.parseLong(topid), user.getId(), markCount);
        try {
            boolean isExist = markService.isExist(mark);
            if (!isExist) {
                boolean result = markService.create(mark);
                if (result) {
                    topService.updateRating(mark.getMark(), Long.parseLong(topid));
                } else {
                    topService.updateRating(mark.getMark() * 2, Long.parseLong(topid));
                }
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute("error_message", "CANT CREATE REPORT, TRY LATER");
        }
    }


}
