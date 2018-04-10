package com.epam.pharmacy.util;

import java.text.ParseException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import com.epam.pharmacy.domain.Prescription;

public class PrescriptionBuilder {

	private static final String EXPIRATION_DATE = "date";
	private static final String COMMENT = "comment";
	private static final String MEDICINE_ID = "medicine_id";
	private static final String CLIENT_ID = "client_id";

	public static Prescription create(HttpServletRequest request) throws ParseException {
		Prescription newPrescription = new Prescription();
		
		Long userId = Long.valueOf(request.getParameter(CLIENT_ID));
		newPrescription.setUserId(userId);
		
		Long medicineId = Long.valueOf(request.getParameter(MEDICINE_ID));
		newPrescription.setMedicineId(medicineId);
		
		String comment = request.getParameter(COMMENT);
		newPrescription.setComment(comment);	
		
		LocalDate expirationLocalDate = DateFormatter.getFormattedDate(request.getParameter(EXPIRATION_DATE));
		newPrescription.setExpirationDate(expirationLocalDate);
		
		return newPrescription;
	}
}
