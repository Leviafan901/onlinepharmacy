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
		@WebInitParam(name = "client", value = "client", description = "role"),
		@WebInitParam(name = "doctor", value = "doctor", description = "role")})
public class SecurityFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityFilter.class);

	private static final String ATTRIBUTE_ROLE = "role";
	private static final String WELCOME_PAGE = "welcome";
	private static final String MAIN_PAGE = "main";

    private String admin;
    private String client;
    private String doctor;

    private final Set<String> guestAccess = new HashSet<>();
    private final Set<String> clientAccess = new HashSet<>();
    private final Set<String> adminAccess = new HashSet<>();
    private final Set<String> doctorAccess = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        LOGGER.info("Initializing roles access");

        admin = filterConfig.getInitParameter("admin");
        client = filterConfig.getInitParameter("client");
        doctor = filterConfig.getInitParameter("doctor");

        initGuest();
        initClient();
        initAdmin();
        initDoctor();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
    		FilterChain filterChain) throws IOException, ServletException {

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
                LOGGER.warn("Can't get permission(client) for path {}", path);
                resp.sendRedirect(MAIN_PAGE);
                return;
            }
        } else if (req.getSession().getAttribute(ATTRIBUTE_ROLE).equals(admin)) {
            if (!adminAccess.contains(path)) {
                LOGGER.warn("Can't get permission(admin) for path {}", path);
                resp.sendRedirect(MAIN_PAGE);
                return;
            }
        } else if (req.getSession().getAttribute(ATTRIBUTE_ROLE).equals(doctor)) {
            if (!doctorAccess.contains(path)) {
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
     * Methods available for doctor guest
     */
    private void initGuest() {
        guestAccess.add("/welcome");
        guestAccess.add("/login");
        guestAccess.add("/set-language");
    }

    /**
     * Methods available for doctor client
     */
    private void initClient() {
    	clientAccess.add("/request-extention-prescription"); 
    	clientAccess.add("/prescription-list");
    	clientAccess.add("/request-list");
    	clientAccess.add("/order-list");
    	clientAccess.add("/make-order");
        clientAccess.add("/main");
        clientAccess.add("/medicine-list");
        clientAccess.add("/login");
        clientAccess.add("/logout");
        clientAccess.add("/set-language");
    }

    /**
     * Methods available for doctor admin
     */
    private void initAdmin() {
    	adminAccess.add("/admin-order-list");
    	adminAccess.add("/creation-form");
    	adminAccess.add("/change-form");
    	adminAccess.add("/create-medicine");
    	adminAccess.add("/change-medicine");
    	adminAccess.add("/delete-medicine");
    	adminAccess.add("/pay-order");
    	adminAccess.add("/cancel-order");
        adminAccess.add("/medicine-list");
        adminAccess.add("/main");
        adminAccess.add("/login");
        adminAccess.add("/logout");
        adminAccess.add("/set-language");
    }

    /**
     * Methods available for doctor role
     */
    private void initDoctor() {
    	doctorAccess.add("/extend-prescription");
    	doctorAccess.add("/reject-request");
    	doctorAccess.add("/request-list");
    	doctorAccess.add("/create-prescription");
    	doctorAccess.add("/prescription-form");
    	doctorAccess.add("/prescription-list");
    	doctorAccess.add("/main");
    	doctorAccess.add("/login");
    	doctorAccess.add("/logout");
    	doctorAccess.add("/set-language");
    }
    
    @Override
    public void destroy() {
        admin = null;
        client = null;
        doctor = null;
        adminAccess.removeAll(adminAccess);
        doctorAccess.removeAll(doctorAccess);
        clientAccess.removeAll(clientAccess);
        guestAccess.removeAll(guestAccess);
    }
}
