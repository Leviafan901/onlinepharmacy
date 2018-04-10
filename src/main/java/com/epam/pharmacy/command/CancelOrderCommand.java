package com.epam.pharmacy.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.OrderService;

public class CancelOrderCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderCommand.class);
	
	private static final String ORDER_ID = "order_id";
	private static final String REFERER = "referer";
	private OrderService orderService;
	
	public CancelOrderCommand(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			Long orderId = Long.valueOf(request.getParameter(ORDER_ID));
		    boolean isCanceled = orderService.cancelOrder(orderId);
		    if (isCanceled) {
		    	return new CommandResult(request.getHeader(REFERER), true);
		    }
		    return null;
	    } catch (ServiceException e) {
			LOGGER.warn("Can't cancel order!", e);
		}
		return null;
	}
}
