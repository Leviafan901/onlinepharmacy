package com.epam.pharmacy.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.services.MedicineService;

public class ChangeMedicineCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChangeMedicineCommand.class);
	
	private static final String MEDICINE_ID = "medicine_id";
	private MedicineService medicineService;
	
	public ChangeMedicineCommand(MedicineService medicineService) {
		this.medicineService = medicineService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
}