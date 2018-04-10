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
import com.epam.pharmacy.dto.AdminOrderDto;
import com.epam.pharmacy.dto.ClientOrderDto;
import com.epam.pharmacy.dto.OrderDto;
import com.epam.pharmacy.exceptions.DaoException;

public class OrderDao extends AbstractDao<Order, OrderDto> {

	private static final String UPDATE_QUERY = "UPDATE pharmacy.`order` SET user_id=? date=? status=? WHERE id = ?";
	private static final String QUERY_CANCEL_ORDER = "UPDATE pharmacy.`order` SET status='canceled' WHERE id = ?";
	private static final String QUERY_PAY_ORDER = "UPDATE pharmacy.`order` SET status='paid' WHERE id = ?";
	private static final String CREATE_QUERY = "INSERT INTO pharmacy.`order` (user_id, date, status) VALUES (?, ?, ?)";
	private static final String SELECT_QUERY_BY_ID = "SELECT user_id, date, status FROM pharmacy.`order` WHERE id = ?";
	private static final String SELECT_QUERY = "SELECT id, user_id, date, status FROM pharmacy.`order`";
	private static final String SELECT_ADMIN_ORDERS_DTO = "SELECT order_id, name, lastname, medicine_name, medicine_id, total_amount, date, status, count, count_in_store FROM (SELECT order_id, medicine_name, medicine_id, user_id, total_amount, date, status, count, count_in_store " + 
															"FROM (SELECT om.order_id, m.name AS medicine_name, m.id AS medicine_id, m.count_in_store, SUM(om.count * m.price) AS total_amount FROM order_medicine om JOIN medicine m ON om.medicine_id = m.id GROUP BY om.order_id) AS orders_count " + 
															"INNER JOIN (SELECT o.id, date, status, o.user_id, count FROM pharmacy.`order` o LEFT JOIN order_medicine m_o ON m_o.order_id = o.id GROUP BY o.id) AS orders_info ON orders_count.order_id = orders_info.id ORDER BY date) AS orders LEFT JOIN user u ON u.id = orders.user_id";											
	private static final String SELECT_CLIENT_ORDERS_DTO_BY_ID = "SELECT order_id, name, total_amount, date, status, count FROM (SELECT om.order_id, m.name, SUM(om.count * m.price) AS total_amount FROM order_medicine om " + 
																"JOIN medicine m ON om.medicine_id = m.id GROUP BY om.order_id) AS orders_count INNER JOIN (SELECT o.id, date, status, count FROM pharmacy.`order` o LEFT JOIN order_medicine m_o ON m_o.order_id = o.id WHERE o.user_id = ? " + 
																"GROUP BY o.id) AS orders_info ON orders_count.order_id = orders_info.id ORDER BY date";
	
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
	
	public List<ClientOrderDto> getAllClientOrdersById(Long clientId) throws DaoException {
		List<ClientOrderDto> orderDtoList = new ArrayList<>();
		try (PreparedStatement statement = createStatement(SELECT_CLIENT_ORDERS_DTO_BY_ID, clientId)) {
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					orderDtoList.add(buildClientOrderDto(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Can't execute query!", e);
		}
		return orderDtoList;
	}
	
	public List<AdminOrderDto> getAllOrdersForAdmin() throws DaoException {
		List<AdminOrderDto> orderDtoList = new ArrayList<>();
		try (PreparedStatement statement = createStatement(SELECT_ADMIN_ORDERS_DTO)) {
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					orderDtoList.add(buildAdminOrderDto(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Can't execute query!", e);
		}
		return orderDtoList;
	}
	
	public boolean cancelOrderById(Long orderId) throws DaoException {
		boolean isCanceled = executeUpdateQuery(QUERY_CANCEL_ORDER, orderId);
		return isCanceled;		
	}
	
	public boolean payOrderById(Long orderId) throws DaoException {
		boolean isPaid = executeUpdateQuery(QUERY_PAY_ORDER, orderId);
		return isPaid;		
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
	
	@Override
	protected OrderDto buildDto(ResultSet resultSet) throws SQLException {
		return null;
	}
	
	private ClientOrderDto buildClientOrderDto(ResultSet resultSet) throws SQLException {
		ClientOrderDto orderDto = new ClientOrderDto();
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
	
	private AdminOrderDto buildAdminOrderDto(ResultSet resultSet) throws SQLException {
		AdminOrderDto orderDto = new AdminOrderDto();
		orderDto.setOrderId(resultSet.getLong("order_id"));
		orderDto.setMedicineId(resultSet.getLong("medicine_id"));
		orderDto.setMedicineName(resultSet.getString("medicine_name"));
		orderDto.setName(resultSet.getString("name"));
		orderDto.setLastname(resultSet.getString("lastname"));
		orderDto.setTotalAmount(resultSet.getInt("total_amount"));
		orderDto.setCountInStore(resultSet.getInt("count_in_store"));
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
			statement.setLong(4, order.getId());
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
