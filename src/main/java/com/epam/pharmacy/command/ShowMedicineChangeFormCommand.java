package com.epam.pharmacy.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.domain.Medicine;
import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.MedicineService;

public class ShowMedicineChangeFormCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowMedicineChangeFormCommand.class);
	
	private static final String MEDICINE_ID = "medicine_id";
	private static final String REFERER = "referer";
	private static final String ATTRIBUTE_MEDICINE = "medicine";

	private static final String MEDICINES_FORM_PAGE = "medicineform";
	private MedicineService medicineService;
	
	public ShowMedicineChangeFormCommand(MedicineService medicineService) {
		this.medicineService = medicineService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Long medicineId = Long.valueOf(request.getParameter(MEDICINE_ID));
		
		try {
			Medicine medicineToUpdate = medicineService.getMedicineById(medicineId);
			if (medicineToUpdate != null) {
		    	request.setAttribute(ATTRIBUTE_MEDICINE, medicineToUpdate);
		    	LOGGER.info("Medicine list transfer to the page.");
		    } else {
		    	return new CommandResult(request.getHeader(REFERER), true);
		    }
		} catch (ServiceException e) {
            LOGGER.warn("Can't find medicine and get him from DB", e);
        }
		return new CommandResult(MEDICINES_FORM_PAGE);
	}
}