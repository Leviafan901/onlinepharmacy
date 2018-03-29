package com.epam.pharmacy.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.OrderService;

public class MakeOrderCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(MakeOrderCommand.class);
	
	private static final String ATTRIBUTE_USER_ID = "userId";
	private static final String ORDER_INFO_PAGE = "orderinfo";
	private static final String SUCCESSED_MESSAGE = "successed_message";
	private static final String FAIL_MESSAGE = "fail_message";
	private OrderService orderService;
	
	public MakeOrderCommand(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		Long orderedMedicineId = Long.valueOf(request.getParameter("medicine_id"));
		Long orderedMedicinesCount = Long.valueOf(request.getParameter("order_count"));
		
		try {
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
			boolean isMade = orderService.makeOrder(userId, orderedMedicineId, orderedMedicinesCount);
			if (isMade) {
				request.setAttribute(SUCCESSED_MESSAGE, true);
				return new CommandResult(ORDER_INFO_PAGE);
			} else {
				request.setAttribute(FAIL_MESSAGE, true);
				return new CommandResult(ORDER_INFO_PAGE);
			}
		}  catch (ServiceException e) {
			LOGGER.warn("Can't make order!", e);
		}
		return null;
	}

}
