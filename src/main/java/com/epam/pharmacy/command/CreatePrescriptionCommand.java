package com.epam.pharmacy.command;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.domain.Prescription;
import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.PrescriptionService;
import com.epam.pharmacy.util.PrescriptionBuilder;

public class CreatePrescriptionCommand implements Command {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(CreatePrescriptionCommand.class);
	
	private static final String ATTRIBUTE_USER_ID = "userId";
	private static final String PRESCRIPTION_INFO_PAGE = "prescriptioninfo";
	private static final String SUCCESSED_MESSAGE = "successed_message";
	private static final String FAIL_MESSAGE = "fail_message";
	private PrescriptionService prescritionService;
	
	public CreatePrescriptionCommand(PrescriptionService prescritionService) {
		this.prescritionService = prescritionService;
	}

	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			Prescription newPrescription = PrescriptionBuilder.create(request);
			HttpSession session = request.getSession();
		    Long doctorId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
		    newPrescription.setDoctorId(doctorId);
		    boolean isCreated = prescritionService.createPrescription(newPrescription);
		    if (isCreated) {
				request.setAttribute(SUCCESSED_MESSAGE, true);
				return new CommandResult(PRESCRIPTION_INFO_PAGE);
			} else {
				request.setAttribute(FAIL_MESSAGE, true);
				return new CommandResult(PRESCRIPTION_INFO_PAGE);
			}
		}  catch (ServiceException | ParseException e) {
			LOGGER.warn("Can't create new prescription!", e);
		}
		return null;
	}
}
