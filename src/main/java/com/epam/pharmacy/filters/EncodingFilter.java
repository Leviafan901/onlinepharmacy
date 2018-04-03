package com.epam.pharmacy.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * Class filter, intended for encoding of incoming data in UTF-8
 *
 * @author Sosenkov Alexei
 */
@WebFilter(filterName = "EncodingFilter", urlPatterns = "/dir/*",
initParams = {@WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding param")})
public class EncodingFilter implements Filter {
    
	private String code;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        code = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
    		FilterChain filterChain) throws IOException, ServletException {
        String codeRequest = servletRequest.getCharacterEncoding();
        if (code != null && !code.equalsIgnoreCase(codeRequest)) {//
            servletRequest.setCharacterEncoding(code);
            servletResponse.setCharacterEncoding(code);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        code = null;
    }
}
