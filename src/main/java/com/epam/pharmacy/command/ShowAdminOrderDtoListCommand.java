package com.epam.pharmacy.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.dto.AdminOrderDto;
import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.OrderService;

public class ShowAdminOrderDtoListCommand implements Command {

private static final Logger LOGGER = LoggerFactory.getLogger(ShowAdminOrderDtoListCommand.class);
	
	private static final String ATTRIBUTE_ORDER_LIST = "orders";
	private static final String SEARCH_ERROR = "search_error";
	private static final String ORDERS_PAGE = "orders";
	private OrderService orderService;
	
	public ShowAdminOrderDtoListCommand(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<AdminOrderDto> orderList = orderService.getAllAdminOrderDto();
			if (!orderList.isEmpty()) {
				request.setAttribute(ATTRIBUTE_ORDER_LIST, orderList);
				LOGGER.info("Order list transfer to the page.");
			} else {
				request.setAttribute(SEARCH_ERROR, true);
			}
		} catch (ServiceException e) {
            LOGGER.warn("Can't get orders from DB", e);
        }
		return new CommandResult(ORDERS_PAGE);
	}
}
