package com.epam.pharmacy.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.domain.enumeration.Role;
import com.epam.pharmacy.dto.PrescriptionDto;
import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.PrescriptionService;

public class ShowPrescriptionListCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShowPrescriptionListCommand.class);
	
	private static final String ATTRIBUTE_USER_ID = "userId";
	private static final String ATTRIBUTE_USER_ROLE = "role";
	private static final String ATTRIBUTE_PRESCRIPTIONS = "prescriptions";
	private static final String PRESCRIPTION_PAGE = "prescriptions";
	private static final String SEARCH_ERROR = "search_error";
	private PrescriptionService prescriptionService;
	
	public ShowPrescriptionListCommand(PrescriptionService prescriptionService) {
		this.prescriptionService = prescriptionService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			Long clientId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
			Role role = (Role) session.getAttribute(ATTRIBUTE_USER_ROLE); 
			List<PrescriptionDto> prescriptionList = prescriptionService.getPrescriptionList(clientId, role);
			if (!prescriptionList.isEmpty()) {
		    	request.setAttribute(ATTRIBUTE_PRESCRIPTIONS, prescriptionList);
		    	LOGGER.info("Prescription list transfer to the page.");
		    } else {
		    	request.setAttribute(SEARCH_ERROR, true);
		    }
		} catch (ServiceException e) {
            LOGGER.warn("Can't find medicines and get them from DB", e);
        }
		return new CommandResult(PRESCRIPTION_PAGE);
	}
}
