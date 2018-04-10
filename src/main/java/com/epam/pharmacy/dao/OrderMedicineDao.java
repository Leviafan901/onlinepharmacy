package com.epam.pharmacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.pharmacy.domain.OrderMedicine;
import com.epam.pharmacy.dto.OrderMedicineDto;
import com.epam.pharmacy.exceptions.DaoException;

public class OrderMedicineDao extends AbstractDao<OrderMedicine, OrderMedicineDto> {

	private static final String UPDATE_QUERY = "UPDATE Order_Medicine SET medicine_id=? order_id=? count=? WHERE id = ?";
	private static final String CREATE_QUERY = "INSERT INTO Order_Medicine (medicine_id, order_id, count) VALUES (?, ?, ?)";
	private static final String SELECT_QUERY_BY_ID = "SELECT medicine_id, order_id, count FROM Order_Medicine WHERE id = ?";
	private static final String SELECT_QUERY = "SELECT id, medicine_id, order_id, count FROM Order_Medicine";
	
	public OrderMedicineDao(Connection connection) throws DaoException {
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
	
	@Override
	protected OrderMedicineDto buildDto(ResultSet resultSet) throws SQLException {
		return null;
	}

	@Override
	protected OrderMedicine build(ResultSet resultSet) throws SQLException {
		OrderMedicine orderMedicine = new OrderMedicine();
		orderMedicine.setId(resultSet.getLong("id"));
		orderMedicine.setMedicineId(resultSet.getLong("medicine_id"));
		orderMedicine.setOrderId(resultSet.getLong("order_id"));
		orderMedicine.setCount(resultSet.getLong("count"));
		return orderMedicine;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement, OrderMedicine orderMedicine)
			throws DaoException {
		try {
			prepareOrderMedicine(statement, orderMedicine);
		} catch (SQLException e) {
			throw new DaoException("Can't prepare statement to insert!", e);
		}
	}

	private void prepareOrderMedicine(PreparedStatement statement, OrderMedicine orderMedicine) throws SQLException {
		statement.setLong(1, orderMedicine.getMedicineId());
		statement.setLong(2, orderMedicine.getOrderId());
		statement.setLong(3, orderMedicine.getCount());
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement statement, OrderMedicine orderMedicine)
			throws DaoException {
		try {
			prepareOrderMedicine(statement, orderMedicine);
			statement.setLong(4, orderMedicine.getId());
		} catch (SQLException e) {
			throw new DaoException("Can't prepare statement to insert!", e);
		}
	}
}
