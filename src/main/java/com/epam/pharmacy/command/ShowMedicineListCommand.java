package com.epam.pharmacy.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.domain.Medicine;
import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.MedicineService;

public class ShowMedicineListCommand implements Command {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShowMedicineListCommand.class);

	private static final String SEARCH_ERROR = "search_error";
	private static final String ATTRIBUTE_MEDICINES = "medicines";
	private static final String MEDICINES_PAGE = "medicines";
	private MedicineService medicineService;

	public ShowMedicineListCommand(MedicineService medicineService) {
	    this.medicineService = medicineService;
    }
	
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {	
		try {
		    List<Medicine> medicineList = medicineService.getMedicineList();
		    if (!medicineList.isEmpty()) {
		    	request.setAttribute(ATTRIBUTE_MEDICINES, medicineList);
		    	LOGGER.info("Medicine list transfer to the page.");
		    } else {
		    	request.setAttribute(SEARCH_ERROR, true);
		    }
		} catch (ServiceException e) {
            LOGGER.warn("Can't find medicines and get them from DB", e);
        }
		return new CommandResult(MEDICINES_PAGE);
	}
}
