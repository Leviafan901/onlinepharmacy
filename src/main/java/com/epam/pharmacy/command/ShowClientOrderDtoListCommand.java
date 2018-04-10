package com.epam.pharmacy.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.dto.ClientOrderDto;
import com.epam.pharmacy.exceptions.ServiceException;
import com.epam.pharmacy.services.OrderService;

public class ShowClientOrderDtoListCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowClientOrderDtoListCommand.class);
	
	private static final String ATTRIBUTE_USER_ID = "userId";
	private static final String ATTRIBUTE_ORDER_LIST = "orders";
	private static final String SEARCH_ERROR = "search_error";
	private static final String ORDERS_PAGE = "orders";
	private OrderService orderService;
	
	public ShowClientOrderDtoListCommand(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			Long clientId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
			List<ClientOrderDto> orderList = orderService.getClientOrders(clientId);
			if (!orderList.isEmpty()) {
				request.setAttribute(ATTRIBUTE_ORDER_LIST, orderList);
				LOGGER.info("Order list transfer to the page.");
			} else {
				request.setAttribute(SEARCH_ERROR, true);
			}
		} catch (ServiceException e) {
            LOGGER.warn("Can't find orders bu client id and get them from DB", e);
        }
		return new CommandResult(ORDERS_PAGE);
	}
}
