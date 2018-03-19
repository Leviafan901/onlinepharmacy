package com.epam.pharmacy.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.domain.enumeration.Role;

/**
 * Class, allows user to logout from app.
 * 
 * @author Sosenkov Alexei
 *
 */
public class LogoutCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogoutCommand.class);
	private static final String ATTRIBUTE_USER_ID = "userId";
	private static final String ATTRIBUTE_ROLE = "role";
	private static final String WELCOME = "welcome";
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
        Long id  = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
        Role role = (Role) session.getAttribute(ATTRIBUTE_ROLE);
        LOGGER.info("Customer by id = {} and by role = {} execute logout.", id , role);
        session.invalidate();
        return new CommandResult(WELCOME, true);
	}
}
