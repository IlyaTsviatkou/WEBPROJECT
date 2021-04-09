package com.example.WEB_App.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "Filter" , urlPatterns = "*.jsp" , initParams = {
        @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param") })
public class EncodingFilter implements Filter {
    private String code;
    public void init(FilterConfig config) throws ServletException {
        code = config.getInitParameter("encoding");
    }

    public void destroy() {
        code = null;
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String ref = req.getHeader("referer");
        String codeRequest = request.getCharacterEncoding();
        HttpSession session = req.getSession();
        session.setAttribute("current_referer", ref);
        if(session.getAttribute("locale") == null) { //fixme just an example , will fix it
            session.setAttribute("locale","UNKNOWN");
        }

        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }
        chain.doFilter(request, response);
    }
}
