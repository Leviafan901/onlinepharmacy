package com.epam.pharmacy.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.RequestService;

public class MakeRequestExtentionPrescriptionCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(MakeRequestExtentionPrescriptionCommand.class);
	
	private static final String DOCTOR_ID = "doctor_id";
	private static final String PRESCRIPTION_ID = "prescription_id";
	private static final String REQUEST_INFO_PAGE = "requestinfo";
	private static final String SUCCESSED_MESSAGE = "successed_message";
	private static final String FAIL_MESSAGE = "fail_message";
	private RequestService requestService;
	
	public MakeRequestExtentionPrescriptionCommand(RequestService requestService) {
		this.requestService = requestService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			Long doctorId = Long.valueOf(request.getParameter(DOCTOR_ID));
			Long prescriptionId = Long.valueOf(request.getParameter(PRESCRIPTION_ID));
			boolean isMaded = requestService.makeRequest(doctorId, prescriptionId);
			if (isMaded) {
				request.setAttribute(SUCCESSED_MESSAGE, true);
				return new CommandResult(REQUEST_INFO_PAGE);
			} else {
				request.setAttribute(FAIL_MESSAGE, true);
				return new CommandResult(REQUEST_INFO_PAGE);
			}
		}  catch (ServiceException e) {
			LOGGER.warn("Can't make request!", e);
		}
		return null;
	}
}
