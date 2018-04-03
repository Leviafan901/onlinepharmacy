package com.epam.pharmacy.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Locale;

/**
 * Class filter, intended to set the current locale settings of
 * the user whose data is stored in the cookies
 *
 * @author Sosenkov Alexei
 */
@WebFilter(filterName = "LocaleFilter", urlPatterns = "/dir/*")
public class LocaleFilter implements Filter {
    
	private static final String LANG = "lang";

	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
		
	}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
    		throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(LANG)) {
                    Locale locale = new Locale(cookie.getValue());
                    Config.set(request.getSession(), Config.FMT_LOCALE, locale);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    	
    }
}
