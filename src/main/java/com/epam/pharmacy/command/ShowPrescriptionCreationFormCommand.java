package com.epam.pharmacy.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.dto.MedicineDto;
import com.epam.pharmacy.dto.UserDto;
import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.MedicineService;
import com.epam.pharmacy.services.UserService;

public class ShowPrescriptionCreationFormCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowPrescriptionCreationFormCommand.class);
	
	private static final String ATTRIBUTE_MEDICINES = "medicines";
	private static final String ATTRIBUTE_CLIENTS = "clients";
	private static final String PRESCRIPTION_FORM_PAGE = "prescriptionform";
	private MedicineService medicineService;
	private UserService userService;
	
	public ShowPrescriptionCreationFormCommand(MedicineService medicineService, UserService userService) {
		this.medicineService = medicineService;
		this.userService = userService;
	}

	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		try {
            List<MedicineDto> medicines = medicineService.getMedicineDtoListForPrescriptionCreation();
            List<UserDto> clients = userService.getUserDtoListForPrescriptionCreation();
            if (!medicines.isEmpty() && !clients.isEmpty()) {
            	request.setAttribute(ATTRIBUTE_MEDICINES, medicines);
            	request.setAttribute(ATTRIBUTE_CLIENTS, clients);
		    	LOGGER.info("Clients and medicines lists transfer to the page.");
            }
		} catch (ServiceException e) {
            LOGGER.warn("Can't find medicines or users and get them from DB", e);
        }
		return new CommandResult(PRESCRIPTION_FORM_PAGE);
	}
}
