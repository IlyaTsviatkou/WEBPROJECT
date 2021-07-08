package com.example.topoftops.controller.filter;

import com.example.topoftops.controller.command.CommandType;
import com.example.topoftops.controller.command.ConfigurationManager;
import com.example.topoftops.controller.command.PagePath;
import com.example.topoftops.entity.User;
import com.example.topoftops.entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_COMMAND;

/**
 * Filter to access by role
 *
 * @author Ilya Tsvetkov
 * @see Filter
 */
@WebFilter(urlPatterns = {"/*"})
public class RoleFilter implements Filter {
    private String ATTRIBUTE_USER = "user";
    private String ATTRIBUTE_ERROR_MESSAGE = "error_message";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(true);
        User user = (User) session.getAttribute(ATTRIBUTE_USER);

        String commandName = request.getParameter(PARAM_NAME_COMMAND);
        if (commandName != null) {
            try {
                CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
                if (!commandType.getAccess().equals(Role.ANY.name())) {
                    if (!(commandType.getAccess().equals(Role.AUTH.name()) && user != null && user.getStatus() == 1)) {
                        if (user == null) {
                            session.setAttribute(ATTRIBUTE_ERROR_MESSAGE, "Invalid access");
                            httpResponse.sendRedirect(httpRequest.getContextPath() + ConfigurationManager.getProperty(PagePath.ERROR));

                            return;
                        } else {
                            if (user.getRole() != Role.valueOf(commandType.getAccess()).ordinal() || user.getStatus() != 1) {
                                session.setAttribute(ATTRIBUTE_ERROR_MESSAGE, "Invalid access");
                                httpResponse.sendRedirect(httpRequest.getContextPath() + ConfigurationManager.getProperty(PagePath.ERROR));
                                return;
                            }
                        }

                    }
                }
            } catch (IllegalArgumentException e) {
                session.setAttribute(ATTRIBUTE_ERROR_MESSAGE, "command not found or wrong!");

                httpResponse.sendRedirect(httpRequest.getContextPath() + ConfigurationManager.getProperty(PagePath.ERROR));
                return;
            }
        }

        chain.doFilter(request, response);
    }


}