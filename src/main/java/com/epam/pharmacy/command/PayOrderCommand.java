package com.epam.pharmacy.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.OrderService;

public class PayOrderCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(PayOrderCommand.class);
	
	private static final String ORDER_ID = "order_id";
	private static final String MEDICINE_COUNT_IN_STORE = "count_in_store";
	private static final String ORDER_COUNT = "count";
	private static final String MEDICINE_ID = "medicine_id";
	private static final String REFERER = "referer";
	private OrderService orderService;
	
	public PayOrderCommand(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			Long orderId = Long.valueOf(request.getParameter(ORDER_ID));
			Long medicineId = Long.valueOf(request.getParameter(MEDICINE_ID));
			Integer orderCount = Integer.valueOf(request.getParameter(ORDER_COUNT));
			Integer medicineCountInStore = Integer.valueOf(request.getParameter(MEDICINE_COUNT_IN_STORE));
		    boolean isPaid = orderService.payOrder(orderId, medicineId, orderCount, medicineCountInStore);
		    if (isPaid) {
		    	return new CommandResult(request.getHeader(REFERER), true);
		    }
		    return null;
	    } catch (ServiceException e) {
			LOGGER.warn("Can't pay order # = {}!", ORDER_ID, e);
		}
		return null;
	}
}
