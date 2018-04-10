package com.epam.pharmacy.services;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.dao.DaoCreator;
import com.epam.pharmacy.dao.MedicineDao;
import com.epam.pharmacy.dao.OrderDao;
import com.epam.pharmacy.dao.OrderMedicineDao;
import com.epam.pharmacy.dao.PrescriptionDao;
import com.epam.pharmacy.domain.Order;
import com.epam.pharmacy.domain.OrderMedicine;
import com.epam.pharmacy.domain.enumeration.OrderStatus;
import com.epam.pharmacy.dto.AdminOrderDto;
import com.epam.pharmacy.dto.ClientOrderDto;
import com.epam.pharmacy.dto.OrderPrescriptionInfo;
import com.epam.pharmacy.exceptions.ConnectionException;
import com.epam.pharmacy.exceptions.DaoException;
import com.epam.pharmacy.exceptions.ServiceException;

public class OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
	
	private static final OrderStatus DEFAULT_ORDER_STATUS = OrderStatus.IN_PROCESS;
	
	/**
	 * Method that check if client have valid prescription, and if he has - 
	 * allow to make new order and make new raw in DB about it,
	 * 
	 * @param Long clientId, orderedMedicineId, orderedMedicinesCount
	 * @return boolean
	 * @throws ServiceException
	 */
	public boolean makeOrder(Long clientId, Long orderedMedicineId, Long orderedMedicinesCount)
			throws ServiceException {
		try (DaoCreator daoCreator = new DaoCreator()) {
			try {
				MedicineDao medicineDao = daoCreator.getMedicineDao();
				boolean prescriptionNeeded = medicineDao.getPrescriptionInfoFromMedicineById(orderedMedicineId);
				if (prescriptionNeeded) {
					LocalDate nowDate = LocalDate.now();
					Date actualDate = Date.valueOf(nowDate);
					PrescriptionDao prescriptionDao = daoCreator.getPrescriptionDao();
					List<OrderPrescriptionInfo> prescriptionDtoList = prescriptionDao
							.getPrescriptionsForOrdersValidation(clientId, orderedMedicineId, actualDate);
					boolean isEmptyList = prescriptionDtoList == null;
					if (isEmptyList) {
						return false;
					}
				}

				daoCreator.startTransaction();
				OrderDao orderDao = daoCreator.getOrderDao();
				Order newOrder = createOrder(clientId);
				Order madedOrder = orderDao.insert(newOrder);

				OrderMedicine newOrderMedicine = new OrderMedicine();
				newOrderMedicine.setMedicineId(orderedMedicineId);
				Long orderId = madedOrder.getId();
				newOrderMedicine.setOrderId(orderId);
				newOrderMedicine.setCount(orderedMedicinesCount);

				OrderMedicineDao orderMedicineDao = daoCreator.getOrderMedicineDao();
				orderMedicineDao.insert(newOrderMedicine);
				daoCreator.commitTransaction();
				return true;
			} catch (DaoException e) {
				daoCreator.rollbackTransaction();
				throw new ServiceException("Can't end transaction and make new order!", e);
			}
		} catch (ConnectionException | SQLException e) {
			throw new ServiceException("Can't make new order!", e);
		}
	}

	private Order createOrder(Long clientId) {
		Order newOrder = new Order();
		newOrder.setUserId(clientId);
		LocalDate nowDate = LocalDate.now();
		newOrder.setDate(nowDate);
		newOrder.setStatus(DEFAULT_ORDER_STATUS);
		return newOrder;
	}
	
	/**
	 * Method that return orderDtoList by client Id (DTO - information about order, ordered medicine)
	 * 
	 * @param clientId
	 * @return OrdersDto by client ID
	 * @throws ServiceException
	 */
	public List<ClientOrderDto> getClientOrders(Long clientId) throws ServiceException {
		List<ClientOrderDto> orderList = new ArrayList<>();
		try (DaoCreator daoCreator = new DaoCreator()) {
			OrderDao orderDao = daoCreator.getOrderDao();
			orderList = orderDao.getAllClientOrdersById(clientId);
			Collections.reverse(orderList);
			LOGGER.info("Find and return all client = {} orders from DB", clientId);
		} catch(DaoException|ConnectionException|SQLException e) {
		    throw new ServiceException("Can't return all client orders from DB", e);
		}
		return orderList;
	}
	
	/***
	 * Method allows to get information about all Orders for user with admin role
	 * 
	 * @return List<AdminOrderDto>
	 * @throws ServiceException
	 */
	public List<AdminOrderDto> getAllAdminOrderDto() throws ServiceException {
		List<AdminOrderDto> orderList = new ArrayList<>();
		try (DaoCreator daoCreator = new DaoCreator()) {
			OrderDao orderDao = daoCreator.getOrderDao();
			orderList = orderDao.getAllOrdersForAdmin();
			Collections.reverse(orderList);
			LOGGER.info("Find and return all orders!");
		} catch(DaoException|ConnectionException|SQLException e) {
		    throw new ServiceException("Can't return all client orders from DB", e);
		}
		return orderList;
	}
	
	/***
	 * Method allows to change status row in Order table in DB to 'cancel'
	 * 
	 * @param Long orderId
	 * @return boolean
	 * @throws ServiceException
	 */
	public boolean cancelOrder(Long orderId) throws ServiceException {
		try (DaoCreator daoCreator = new DaoCreator()) {
			try {
				OrderDao orderDao = daoCreator.getOrderDao();
				daoCreator.startTransaction();
				boolean isCanceled = orderDao.cancelOrderById(orderId);
				daoCreator.commitTransaction();
				return isCanceled;
			} catch (DaoException e) {
				daoCreator.rollbackTransaction();
				throw new ServiceException("Can't end transaction and cancel order!", e);
			}
		} catch (ConnectionException | SQLException e) {
			throw new ServiceException("Can't cancel order!", e);
		}
	}
	
	/***
	 * Method allows to change status row in Order table in DB to 'paid'  and
	 * change count_in_order in the Medicine table
	 * 
	 * @param Long orderId, Long medicineId, Integer orderCount, Integer medicineCountInStore
	 * @return boolean
	 * @throws ServiceException
	 */
	public boolean payOrder(Long orderId, Long medicineId, Integer orderCount, Integer medicineCountInStore)
			throws ServiceException {
		try (DaoCreator daoCreator = new DaoCreator()) {
			try {
				boolean isAvaiableOrder = orderCount <= medicineCountInStore;
				if (isAvaiableOrder) {
					OrderDao orderDao = daoCreator.getOrderDao();
					MedicineDao medicineDao = daoCreator.getMedicineDao();
					daoCreator.startTransaction();
					boolean isPaid = orderDao.payOrderById(orderId);
					Integer currentMedicineCountInStore = medicineCountInStore - orderCount;
					boolean isChangeCount = medicineDao.changeMedicineCountInStoreById(currentMedicineCountInStore,
							medicineId);
					daoCreator.commitTransaction();
					if (isPaid && isChangeCount) {
						return true;
					} else {
						return false;
					}
				} else {
					throw new ServiceException("Can't pay order! There is not so much medicine in the store!");
				}
			} catch (DaoException e) {
				daoCreator.rollbackTransaction();
				throw new ServiceException("Can't end transaction and pay order!", e);
			}
		} catch (ConnectionException | SQLException e) {
			throw new ServiceException("Can't pay order!", e);
		}
	}
}
