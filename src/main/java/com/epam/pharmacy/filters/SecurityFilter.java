package com.epam.pharmacy.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

/**
 * Class filter, intended to restrict access for different types of users
 *
 * @author Sosenkov Alexei
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = "/dir/*",
initParams = {@WebInitParam(name = "admin", value = "admin", description = "role"),
		@WebInitParam(name = "client", value = "client", description = "role")})
public class SecurityFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityFilter.class);

	private static final String ATTRIBUTE_ROLE = "role";
	private static final String WELCOME_PAGE = "welcome";
	private static final String MAIN_PAGE = "main";

    private String admin;
    private String client;

    private final Set<String> guestAccess = new HashSet<>();
    private final Set<String> clientAccess = new HashSet<>();
    private final Set<String> adminAccess = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        LOGGER.info("Initializing roles access");

        admin = filterConfig.getInitParameter("admin");
        client = filterConfig.getInitParameter("client");

        initGuest();
        initUser();
        initAdmin();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String path = req.getPathInfo();

        if (req.getSession().getAttribute(ATTRIBUTE_ROLE) == null) {
            if (!guestAccess.contains(path)) {
                LOGGER.warn("Can't get permission(guest) for path {}", path);
                resp.sendRedirect(WELCOME_PAGE);
                return;
            }
        } else if (req.getSession().getAttribute(ATTRIBUTE_ROLE).equals(client)) {
            if (!clientAccess.contains(path)) {
                LOGGER.warn("Can't get permission(user) for path {}", path);
                resp.sendRedirect(MAIN_PAGE);
                return;
            }
        } else if (req.getSession().getAttribute(ATTRIBUTE_ROLE).equals(admin)) {
            if (!adminAccess.contains(path)) {
                LOGGER.warn("Can't get permission(admin) for path {}", path);
                resp.sendRedirect(MAIN_PAGE);
                return;
            }
        } else {
            filterChain.doFilter(req, resp);
            return;
        }
        filterChain.doFilter(req, resp);


    }

    /**
     * Method , access for guest
     */
    private void initGuest() {
        guestAccess.add("/welcome");
        guestAccess.add("/login");
        guestAccess.add("/set-language");
    }

    /**
     * Method , access for client
     */
    private void initUser() {
       // userAccess.add("/deptCustomerBook");
       // userAccess.add("/takeBook");
        //userAccess.add("/takeBookBasket");
        //userAccess.add("/returnBook");
        //userAccess.add("/basket");
       // userAccess.add("/returnCustomerBook");
       // userAccess.add("/basket-delete");

        //userAccess.add("/profileEdit");
       // userAccess.add("/email-edit");
       // userAccess.add("/password-edit");
        //userAccess.add("/aboutBook");
        clientAccess.add("/main");
        //userAccess.add("/account");
        clientAccess.add("/login");
        clientAccess.add("/logout");
        clientAccess.add("/set-language");
    }

    /**
     * Method , access for guest admin
     */
    private void initAdmin() {
       // adminAccess.add("/readers");
       // adminAccess.add("/management");
       // adminAccess.add("/returnCustomerBook");
       // adminAccess.add("/registerBook");
       // adminAccess.add("/personalDataEdit");
        //adminAccess.add("/bookEdit");
        //adminAccess.add("/aboutReader");
        //adminAccess.add("/adminReturnBook");
       // adminAccess.add("/deleteBook");
        //adminAccess.add("/deleteProfile");
        //adminAccess.add("/deleteBookError");
       // adminAccess.add("/deleteProfileError");

       // adminAccess.add("/profileEdit");
       // adminAccess.add("/email-edit");
        //adminAccess.add("/password-edit");
        //adminAccess.add("/aboutBook");
        adminAccess.add("/main");
       // adminAccess.add("/account");
        adminAccess.add("/login");
        adminAccess.add("/logout");
        adminAccess.add("/set-language");
    }

    @Override
    public void destroy() {
        admin = null;
        client = null;
        adminAccess.removeAll(adminAccess);
        clientAccess.removeAll(clientAccess);
        guestAccess.removeAll(guestAccess);
    }
}
