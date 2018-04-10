package com.epam.pharmacy.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowMedicineCreationFormCommand implements Command {

    private static final String ACTUAL_FORM = "actualForm";
    private static final String CREATION_FORM = "creationForm";
	private static final String MEDICINES_FORM_PAGE = "medicineform";
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(ACTUAL_FORM, CREATION_FORM);
		return new CommandResult(MEDICINES_FORM_PAGE);
	}
}
