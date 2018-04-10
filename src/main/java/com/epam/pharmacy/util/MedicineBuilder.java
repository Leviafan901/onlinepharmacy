package com.epam.pharmacy.util;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import com.epam.pharmacy.domain.Medicine;

public class MedicineBuilder {

	private static final String NAME = "name";
	private static final String NEED_PRESCRIPTION = "needPrescription";
	private static final String PRICE = "price";
	private static final String DOSAGE_MG = "dosageMg";
	private static final String COUNT = "count";
	private static final String COUNT_IN_STORE = "countInStore";

	public static Medicine createMedicine(HttpServletRequest request) {
		Medicine newMedicine = new Medicine();
		String medicineName = request.getParameter(NAME);
		newMedicine.setName(medicineName);

		Long medicineCountInStore = Long.valueOf(request.getParameter(COUNT_IN_STORE));
		newMedicine.setCountInStore(medicineCountInStore);

		Integer medicineCount = Integer.valueOf(request.getParameter(COUNT));
		newMedicine.setCount(medicineCount);

		Integer medicineDosageMg = Integer.valueOf(request.getParameter(DOSAGE_MG));
		newMedicine.setDosageMg(medicineDosageMg);

		Double doublePrice = Double.valueOf(request.getParameter(PRICE));
		BigDecimal medicinePrice = BigDecimal.valueOf(doublePrice);
		newMedicine.setPrice(medicinePrice);

		Boolean medicineNeedPrescriptionInBox = Boolean.valueOf(request.getParameter(NEED_PRESCRIPTION));
		boolean medicineNeedPrescription = medicineNeedPrescriptionInBox;
		newMedicine.setNeedPrescription(medicineNeedPrescription);
		
		return newMedicine;
	}
}
