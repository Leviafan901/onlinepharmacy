package com.epam.pharmacy.services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.dao.DaoCreator;
import com.epam.pharmacy.dao.OrderDao;
import com.epam.pharmacy.dao.OrderMedicineDao;
import com.epam.pharmacy.domain.Order;
import com.epam.pharmacy.domain.OrderMedicine;
import com.epam.pharmacy.domain.enumeration.OrderStatus;
import com.epam.pharmacy.dto.OrderDto;
import com.epam.pharmacy.exceptions.ConnectionException;
import com.epam.pharmacy.exceptions.DaoException;
import com.epam.pharmacy.exceptions.ServiceException;

public class OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
	
	private static final OrderStatus ORDER_STATUS = OrderStatus.IN_PROCESS;
	
	/**
	 * Method, allow to make new order and make new raw in DB about it
	 * @param userId 
	 * 
	 * @throws ServiceException
	 */
	public boolean makeOrder(Long userId, Long orderedMedicineId, Long orderedMedicinesCount)
			throws ServiceException {
		Order newOrder = new Order();
		newOrder.setUserId(userId);
		LocalDate nowDate = LocalDate.now();
		newOrder.setDate(nowDate);
		newOrder.setStatus(ORDER_STATUS);
		try (DaoCreator daoCreator = new DaoCreator()) {
			try {	
					OrderDao orderDao = daoCreator.getOrderDao();
					OrderMedicineDao orderMedicineDao = daoCreator.getOrderMedicineDao();
					
					daoCreator.startTransaction();
					Order madedOrder = orderDao.insert(newOrder);
					Long orderId = madedOrder.getId();
					OrderMedicine newOrderMedicine = new OrderMedicine();
					newOrderMedicine.setMedicineId(orderedMedicineId);
					newOrderMedicine.setOrderId(orderId);
					newOrderMedicine.setCount(orderedMedicinesCount);
					orderMedicineDao.insert(newOrderMedicine);
					daoCreator.commitTransaction();
					return true;
				} catch(DaoException e) {
				    daoCreator.rollbackTransaction();
					throw new ServiceException("Can't end transaction and make new order!", e);
				}
		} catch (ConnectionException | SQLException e) {
			throw new ServiceException("Can't make new order!", e);
		}
	}
	
	/**
	 * Method that return orderDtoList by client Id (DTO - information about order, ordered medicine)
	 * 
	 * @param clientId
	 * @return OrdersDto by client ID
	 * @throws ServiceException
	 */
	public List<OrderDto> getClientOrders(Long clientId) throws ServiceException {
		List<OrderDto> orderList = new ArrayList<>();
		try (DaoCreator daoCreator = new DaoCreator()) {
			OrderDao orderDao = daoCreator.getOrderDao();
			orderList = orderDao.getAllClientOrdersById(clientId);
			LOGGER.info("Find and return all client = {} orders from DB", clientId);
		} catch(DaoException|ConnectionException|SQLException e) {
		    throw new ServiceException("Can't return all client orders from DB", e);
		}
		return orderList;
	}
}
