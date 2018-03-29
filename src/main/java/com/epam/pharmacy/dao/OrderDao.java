package com.epam.pharmacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import com.epam.pharmacy.domain.Order;
import com.epam.pharmacy.domain.enumeration.OrderStatus;
import com.epam.pharmacy.dto.OrderDto;
import com.epam.pharmacy.exceptions.DaoException;

public class OrderDao extends AbstractDao<Order> {

	private static final String UPDATE_QUERY = "UPDATE pharmacy.`order` SET user_id=? date=? status=? WHERE id = ?";
	private static final String CREATE_QUERY = "INSERT INTO pharmacy.`order` (user_id, date, status) VALUES (?, ?, ?)";
	private static final String SELECT_QUERY_BY_ID = "SELECT user_id, date, status FROM pharmacy.`order` WHERE id = ?";
	private static final String SELECT_QUERY = "SELECT id, user_id, date, status FROM pharmacy.`order`";
	private static final String SELECT_CLIENT_ORDERS_DTO_BY_ID = "SELECT order_id, name, total_amount, prescription_id, date, status, count FROM (SELECT om.order_id, m.name, SUM(om.count * m.price) AS total_amount FROM order_medicine om" + 
																" JOIN medicine m ON om.medicine_id = m.id GROUP BY om.order_id) AS orders_count INNER JOIN (SELECT o.id, prescription_id, date, status, count FROM pharmacy.`order` o LEFT JOIN order_medicine m_o ON m_o.order_id = o.id WHERE o.user_id = ?" + 
																" GROUP BY o.id) AS orders_info ON orders_count.order_id = orders_info.id ORDER BY date";
	
	public OrderDao(Connection connection) throws DaoException {
		super(connection);
	}

	@Override
	public String getSelectQuery() {
		return SELECT_QUERY;
	}

	@Override
	public String getQueryById() {
		return SELECT_QUERY_BY_ID;
	}

	@Override
	public String getCreateQuery() {
		return CREATE_QUERY;
	}

	@Override
	public String getUpdateQuery() {
		return UPDATE_QUERY;
	}
	
	public List<OrderDto> getAllClientOrdersById(Long clientId) throws DaoException {
		List<OrderDto> orderDtoList = new ArrayList<>();
		try (PreparedStatement statement = createStatement(SELECT_CLIENT_ORDERS_DTO_BY_ID, clientId)) {
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					orderDtoList.add(buildOrderDto(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Can't execute query!", e);
		}
		return orderDtoList;
	}

	@Override
	protected Order build(ResultSet resultSet) throws SQLException {
		Order order = new Order();
		order.setId(resultSet.getLong("id"));
		order.setUserId(resultSet.getLong("user_id"));
		Date date = resultSet.getDate("date");//
		LocalDate orderDate = date.toLocalDate();
		order.setDate(orderDate);
		String status = resultSet.getString("status")
				.toUpperCase();
		order.setStatus(OrderStatus.valueOf(status));
		return order;
	}
	
	private OrderDto buildOrderDto(ResultSet resultSet) throws SQLException {
		OrderDto orderDto = new OrderDto();
		orderDto.setOrderId(resultSet.getLong("order_id"));
		orderDto.setName(resultSet.getString("name"));
		orderDto.setTotalAmount(resultSet.getInt("total_amount"));
		orderDto.setCount(resultSet.getLong("count"));
		Date date = resultSet.getDate("date");//
		LocalDate orderDate = date.toLocalDate();
		orderDto.setDate(orderDate);
		String status = resultSet.getString("status")
				.toUpperCase();
		orderDto.setStatus(OrderStatus.valueOf(status));
		return orderDto;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement, Order order) throws DaoException {
		try {
			prepareOrder(statement, order);
		} catch (SQLException e) {
			throw new DaoException("Can't prepare statement to insert!", e);
		}
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement statement, Order order) throws DaoException {
		try {
			prepareOrder(statement, order);
			statement.setLong(5, order.getId());
		} catch (SQLException e) {
			throw new DaoException("Can't prepare statement to insert!", e);
		}
	}
	
	private void prepareOrder(PreparedStatement statement, Order order) throws SQLException {
		statement.setLong(1, order.getUserId());
		LocalDate localDate = order.getDate();
		Date date = Date.valueOf(localDate);
		statement.setDate(2, date);
		String status = String.valueOf(order.getStatus());
		statement.setString(3, status);
	}
}
