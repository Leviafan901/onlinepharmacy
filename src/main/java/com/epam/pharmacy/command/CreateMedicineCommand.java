package com.epam.pharmacy.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.domain.Medicine;
import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.MedicineService;
import com.epam.pharmacy.util.MedicineBuilder;

public class CreateMedicineCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateMedicineCommand.class);

	private static final String MEDICINES_INFO_PAGE = "medicineinfo";
	private static final String SUCCESSED_CREATION_MESSAGE = "successed_creation_message";
	private static final String FAIL_CREATION_MESSAGE = "fail_creation_message";
	private MedicineService medicineService;
	
	public CreateMedicineCommand(MedicineService medicineService) {
		this.medicineService = medicineService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			Medicine newMedicine = MedicineBuilder.createMedicine(request);
			boolean isMade = medicineService.createMedicine(newMedicine);
			if (isMade) {
				request.setAttribute(SUCCESSED_CREATION_MESSAGE, true);
				return new CommandResult(MEDICINES_INFO_PAGE);
			} else {
				request.setAttribute(FAIL_CREATION_MESSAGE, true);
				return new CommandResult(MEDICINES_INFO_PAGE);
			}
		}  catch (ServiceException e) {
			LOGGER.warn("Can't make order!", e);
		}
		return null;
	}
}
