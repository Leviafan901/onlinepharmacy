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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doctorId == null) ? 0 : doctorId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((prescriptionId == null) ? 0 : prescriptionId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Request other = (Request) obj;
		if (doctorId == null) {
			if (other.doctorId != null) {
				return false;
			}
		} else if (!doctorId.equals(other.doctorId)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (prescriptionId == null) {
			if (other.prescriptionId != null) {
				return false;
			}
		} else if (!prescriptionId.equals(other.prescriptionId)) {
			return false;
		}
		if (status != other.status) {
			return false;
		}
		return true;
	}
}
