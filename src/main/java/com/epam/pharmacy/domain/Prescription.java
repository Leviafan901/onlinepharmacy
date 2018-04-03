package com.epam.pharmacy.domain;


import java.time.LocalDate;

import com.epam.pharmacy.dao.Identifiable;

public class Prescription implements Identifiable {

	private Long id;
	private Long doctorId;
	private Long userId;
	private Long medicineId;
	private LocalDate creationDate;
	private LocalDate expirationDate;
	private String comment;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getDoctorId() {
		return doctorId;
	}
	
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(Long medicineId) {
		this.medicineId = medicineId;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	
	public LocalDate getExpirationDate() {
		return expirationDate;
	}
	
	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
}
