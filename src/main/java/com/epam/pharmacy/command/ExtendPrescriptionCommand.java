package com.epam.pharmacy.command;

import java.text.ParseException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.PrescriptionService;
import com.epam.pharmacy.util.DateFormatter;

public class ExtendPrescriptionCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExtendPrescriptionCommand.class);
	
	private static final String PRESCRIPTION_ID = "prescription_id";
	private static final String REQUEST_ID = "request_id";
	private static final String REFERER = "referer";
	private PrescriptionService prescriptionService;
	
	public ExtendPrescriptionCommand(PrescriptionService prescriptionService) {
		this.prescriptionService = prescriptionService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			Long prescriptionId = Long.valueOf(request.getParameter(PRESCRIPTION_ID));
			Long requestId = Long.valueOf(request.getParameter(REQUEST_ID));
			LocalDate expirationDate = DateFormatter.getFormattedDate(request.getParameter("expiration_date"));
		    boolean isExtented = prescriptionService.extendPrescription(prescriptionId, requestId, expirationDate);
		    if (isExtented) {
		    	return new CommandResult(request.getHeader(REFERER), true);
		    }
		    return null;
	    } catch (ServiceException | ParseException e) {
			LOGGER.warn("Can't extend prescription # = {}!", PRESCRIPTION_ID, e);
		}
		return null;
	}
}
