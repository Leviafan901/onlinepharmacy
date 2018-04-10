package com.epam.pharmacy.dto;

import java.time.LocalDate;

import com.epam.pharmacy.domain.enumeration.RequestStatus;

public class RequestDto implements Dto {

	private Long doctorId;
	private Long requestId;
	private Long prescriptionId;
	private RequestStatus status;
	private LocalDate creationDate;
	private LocalDate expirationDate;
	private String userName;
	private String userLastname;
	
	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public Long getRequestId() {
		return requestId;
	}
	
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	
	public Long getPrescriptionId() {
		return prescriptionId;
	}
	
	public void setPrescriptionId(Long prescriptionId) {
		this.prescriptionId = prescriptionId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserLastname() {
		return userLastname;
	}

	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}
}
