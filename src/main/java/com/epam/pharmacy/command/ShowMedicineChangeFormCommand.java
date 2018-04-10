package com.epam.pharmacy.command;

import java.util.Optional;

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
	private static final String ACTUAL_FORM = "actualForm";
	private static final String CHANGE_FORM = "changeForm";
	private MedicineService medicineService;
	
	public ShowMedicineChangeFormCommand(MedicineService medicineService) {
		this.medicineService = medicineService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			Long medicineId = Long.valueOf(request.getParameter(MEDICINE_ID));
			Optional<Medicine> optionalMedicine = medicineService.getMedicineById(medicineId);
			if (optionalMedicine.isPresent()) {
				Medicine medicineToUpdate = optionalMedicine.get();
		    	request.setAttribute(ATTRIBUTE_MEDICINE, medicineToUpdate);
		    	request.setAttribute(ACTUAL_FORM, CHANGE_FORM);
		    	LOGGER.info("Medicine transfer to the page.");
		    } else {
		    	return new CommandResult(request.getHeader(REFERER), true);
		    }
		} catch (ServiceException e) {
            LOGGER.warn("Can't find medicine and get it from DB", e);
            return new CommandResult(request.getHeader(REFERER), true);
        }
		return new CommandResult(MEDICINES_FORM_PAGE);
	}
}