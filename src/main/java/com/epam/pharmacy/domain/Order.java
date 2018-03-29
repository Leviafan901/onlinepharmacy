package com.epam.pharmacy.domain;

import java.time.LocalDate;

import com.epam.pharmacy.dao.Identifiable;
import com.epam.pharmacy.domain.enumeration.OrderStatus;

public class Order implements Identifiable {

	private Long id;
	private Long userId;
	private LocalDate date;
	private OrderStatus status;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
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
	
}
