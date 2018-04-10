package com.epam.pharmacy.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.MedicineService;

public class DeleteMedicineCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteMedicineCommand.class);
	
	private static final String MEDICINE_ID = "medicine_id";
	private static final String REFERER = "referer";
	private MedicineService medicineService;
	
	public DeleteMedicineCommand(MedicineService medicineService) {
		this.medicineService = medicineService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			Long medicineId = Long.valueOf(request.getParameter(MEDICINE_ID));
		    boolean isDeleted = medicineService.deleteMedicine(medicineId);
		    if (isDeleted) {
		    	return new CommandResult(request.getHeader(REFERER), true);
		    }
		    return null;
	    } catch (ServiceException e) {
			LOGGER.warn("Can't delete medicine!", e);
		}
		return null;
	} 
}
