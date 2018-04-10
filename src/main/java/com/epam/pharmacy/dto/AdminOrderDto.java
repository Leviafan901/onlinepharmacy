package com.epam.pharmacy.dto;

import java.time.LocalDate;

import com.epam.pharmacy.domain.enumeration.OrderStatus;

public class AdminOrderDto implements OrderDto {
	
	private Long orderId;
	private String medicineName;
	private Long medicineId;
	private LocalDate date;
	private OrderStatus status;
	private Long count;
	private Integer totalAmount;
	private Integer countInStore;
	private String name;
	private String lastname;
	
	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public Integer getCountInStore() {
		return countInStore;
	}

	public void setCountInStore(Integer countInStore) {
		this.countInStore = countInStore;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public OrderStatus getStatus() {
		return status;
	}
	
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
	
	public Integer getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public Long getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(Long medicineId) {
		this.medicineId = medicineId;
	}
}
