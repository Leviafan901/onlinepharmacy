package com.epam.pharmacy.domain;

import com.epam.pharmacy.dao.Identifiable;
import com.epam.pharmacy.domain.enumeration.RequestStatus;

public class Request implements Identifiable {

	private Long id;
	private Long doctorId;
	private Long prescriptionId;
	private RequestStatus status;
	
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
	
	public Long getPrescriptionId() {
		return prescriptionId;
	}
	
	public void setPrescriptionId(Long prescriptionId) {
		this.prescriptionId = prescriptionId;
	}
	
	public RequestStatus getStatus() {
		return status;
	}
	
	public void setStatus(RequestStatus status) {
		this.status = status;
	}
}
