package com.example.topoftops.controller.command.impl;

import com.example.topoftops.controller.command.Command;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.controller.command.Router;
import com.example.topoftops.controller.command.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_LANGUAGE;

/**
 * The command is responsible for changing local
 *
 * @author Ilya Tsvetkov
 * @see Command
 */
public class ChangeLocalCommand implements Command {
    private static final String RUS = "rus";
    private static final String RUS_FORMAT = "ru_RU";
    private static final String ENG = "eng";
    private static final String ENG_FORMAT = "en_US";
    private static final String ATTRIBUTE_LOCALE = "locale";

    public ChangeLocalCommand() {
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String page = ConfigurationManager.getProperty(PagePath.INDEX);
        HttpSession session = request.getSession();
        String lang = request.getParameter(PARAM_NAME_LANGUAGE);
        if (lang.equals(RUS)) {
            session.setAttribute(ATTRIBUTE_LOCALE, RUS_FORMAT);
        } else if (lang.equals(ENG)) {
            session.setAttribute(ATTRIBUTE_LOCALE, ENG_FORMAT);
        }
        router = new Router(page, Router.RouteType.REDIRECT);
        return router;
    }
}
