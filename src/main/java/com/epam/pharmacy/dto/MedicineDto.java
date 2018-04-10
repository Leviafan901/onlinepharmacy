package com.epam.pharmacy.dto;

public class MedicineDto implements Dto {

	private Long medicineId;
	private String medicineName;
	
	public Long getMedicineId() {
		return medicineId;
	}
	
	public void setMedicineId(Long medicineId) {
		this.medicineId = medicineId;
	}
	
	public String getMedicineName() {
		return medicineName;
	}
	
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
}
