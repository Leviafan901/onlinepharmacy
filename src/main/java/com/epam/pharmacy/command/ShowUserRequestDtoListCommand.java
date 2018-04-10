package com.epam.pharmacy.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.domain.enumeration.Role;
import com.epam.pharmacy.dto.RequestDto;
import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.RequestService;

public class ShowUserRequestDtoListCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShowUserRequestDtoListCommand.class);
	
	private static final String ATTRIBUTE_USER_ID = "userId";
	private static final String ATTRIBUTE_ROLE = "role";
	private static final String ATTRIBUTE_REQUESTS = "requests";
	private static final String REQUESTS_PAGE = "requests";
	private static final String SEARCH_ERROR = "search_error";
	private RequestService requestService;
	
	public ShowUserRequestDtoListCommand(RequestService requestService) {
		this.requestService = requestService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
			Role role = (Role) session.getAttribute(ATTRIBUTE_ROLE);
			List<RequestDto> requestDtoList = requestService.getUserRequests(userId, role);
			if (!requestDtoList.isEmpty()) {
		    	request.setAttribute(ATTRIBUTE_REQUESTS, requestDtoList);
		    	LOGGER.info("Prescription list transfer to the page.");
		    } else {
		    	request.setAttribute(SEARCH_ERROR, true);
		    }
		} catch (ServiceException e) {
            LOGGER.warn("Can't find medicines and get them from DB", e);
        }
		return new CommandResult(REQUESTS_PAGE);
	}
}
