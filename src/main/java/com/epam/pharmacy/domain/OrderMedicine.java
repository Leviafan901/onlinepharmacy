package com.epam.pharmacy.domain;

import com.epam.pharmacy.dao.Identifiable;

public class OrderMedicine implements Identifiable {
	
	private Long id;
	private Long medicineId;
	private Long orderId;
	private Long count;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getMedicineId() {
		return medicineId;
	}
	
	public void setMedicineId(Long medicineId) {
		this.medicineId = medicineId;
	}
	
	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
}
