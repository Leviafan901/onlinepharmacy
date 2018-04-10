package com.epam.pharmacy.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.domain.Medicine;
import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.MedicineService;
import com.epam.pharmacy.util.MedicineBuilder;

public class ChangeMedicineCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChangeMedicineCommand.class);
	
	private static final String MEDICINE_ID = "medicine_id";
	private static final String MEDICINES_INFO_PAGE = "medicineinfo";
	private static final String REFERER = "referer";
	private static final String SUCCESSED_MESSAGE = "successed_message";
	private static final String FAIL_MESSAGE = "fail_message";
	private MedicineService medicineService;
	
	public ChangeMedicineCommand(MedicineService medicineService) {
		this.medicineService = medicineService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			Medicine medicineToUpdate = MedicineBuilder.createMedicine(request);
			Long medicineId = Long.valueOf(request.getParameter(MEDICINE_ID));
			medicineToUpdate.setId(medicineId);
			boolean isUpdated = medicineService.updateMedicine(medicineToUpdate);
			if (isUpdated) {
		    	LOGGER.info("Medicine is updated!");
		    	request.setAttribute(SUCCESSED_MESSAGE, true);
		    	return new CommandResult(MEDICINES_INFO_PAGE);
		    } else {
		    	request.setAttribute(FAIL_MESSAGE, true);
		    	return new CommandResult(MEDICINES_INFO_PAGE);
		    }
		} catch (ServiceException e) {
            LOGGER.warn("Can't update medicine to DB!", e);
            return new CommandResult(request.getHeader(REFERER), true);
       }
	}
}