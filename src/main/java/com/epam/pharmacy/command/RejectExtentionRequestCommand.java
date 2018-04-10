package com.epam.pharmacy.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.RequestService;

public class RejectExtentionRequestCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(RejectExtentionRequestCommand.class);
	
	private static final String REQUEST_ID = "request_id";
	private static final String REFERER = "referer";
	private RequestService requestService;
	
	public RejectExtentionRequestCommand(RequestService requestService) {
		this.requestService = requestService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			Long requestId = Long.valueOf(request.getParameter(REQUEST_ID));
			boolean isRejected = requestService.rejectRequest(requestId);
			if (isRejected) {
				return new CommandResult(request.getHeader(REFERER), true);
			}
			return null;
		} catch (ServiceException e) {
			LOGGER.warn("Can't cancel order!", e);
		}
		return null;
	}
}
