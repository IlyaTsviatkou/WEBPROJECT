package com.example.topoftops.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.topoftops.controller.command.RequestParam.PARAM_NAME_LOCALE;

/**
 * Filter to encoding and CORS
 *
 * @author Ilya Tsvetkov
 * @see Filter
 */
@WebFilter(filterName = "Filter", urlPatterns = "/*", initParams = {
        @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})
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
        if (session.getAttribute(PARAM_NAME_LOCALE) == null) {
            session.setAttribute(PARAM_NAME_LOCALE, "UNKNOWN");
        }

        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }
        HttpServletRequest sRequest = (HttpServletRequest) request;
        HttpServletResponse sResponse = (HttpServletResponse) response;
        sResponse.setHeader("Access-Control-Allow-Origin", "*");
        sResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT");
        sResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");
        if (sRequest.getMethod().equals("OPTIONS")) {
            sResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }
        chain.doFilter(request, response);
    }
}
