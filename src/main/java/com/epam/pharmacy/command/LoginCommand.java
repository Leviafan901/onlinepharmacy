package com.epam.pharmacy.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.domain.User;
import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.UserService;
import com.epam.pharmacy.util.Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Action class , allows to execute login.
 *
 * @author Sosenkov Alexei
 */
public class LoginCommand implements Command {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class);
	
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String WELCOME = "welcome";
	private static final String ATTRIBUTE_USER_ID = "userId";
	private static final String ATTRIBUTE_ROLE = "role";
	private static final String ATTRIBUTE_NAME = "name";
	private static final String MAIN_PAGE = "main";
	private static final String LOGIN_ERROR = "login_error";

    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = new UserService();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        try {
            User user = userService.findUserByLoginAndPassword(login, Encoder.encode(password));
            boolean isIncorrectUser = user == null;
            if (!isIncorrectUser) {
                HttpSession session = request.getSession();
                session.setAttribute(ATTRIBUTE_USER_ID, user.getId());
                session.setAttribute(ATTRIBUTE_ROLE, user.getRole());
                session.setAttribute(ATTRIBUTE_NAME, user.getName());
                LOGGER.info("User by id = {} and role = {} login in system ", user.getId(), user.getRole());
                return new CommandResult(MAIN_PAGE, true);
            } else {
                request.setAttribute(LOGIN_ERROR, true);
                return new CommandResult(WELCOME);
            }
        } catch (ServiceException e) {
            LOGGER.warn("Can't find user by login and password", e);
        }
        return null;
    }
}
