package com.epam.pharmacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.pharmacy.domain.Medicine;
import com.epam.pharmacy.exceptions.DaoException;

public class MedicineDao extends AbstractDao<Medicine> {

	private static final String UPDATE_QUERY = "UPDATE Medicine SET name=? count_in_store=? count=? dosage_mg=? need_prescription=? price=? deleted=? WHERE id = ?;";
	private static final String CREATE_QUERY = "INSERT INTO Medicine (name, count_in_store, count, dosage_mg, need_prescription, price, deleted) VALUES (?, ?, ?, ?, ?, ?, ?);";
	private static final String SELECT_QUERY_BY_ID = "SELECT name, count_in_store, count, dosage_mg, need_prescription, price, deleted FROM Medicine WHERE id = ?;";
	private static final String SELECT_QUERY = "SELECT id, name, count_in_store, count, dosage_mg, need_prescription, price, deleted FROM Medicine WHERE count_in_store <> 0 AND deleted <> 1";

	public MedicineDao(Connection connection) throws DaoException {
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
	protected Medicine build(ResultSet resultSet) throws SQLException {
		Medicine medicine = new Medicine();
		medicine.setId(resultSet.getLong("id"));
		medicine.setName(resultSet.getString("name"));
		medicine.setCountInStore(resultSet.getLong("count_in_store"));
		medicine.setCount(resultSet.getInt("count"));
		medicine.setDosageMg(resultSet.getInt("dosage_mg"));
		medicine.setNeedPrescription(resultSet.getBoolean("need_prescription"));
		medicine.setPrice(resultSet.getBigDecimal("price"));
		medicine.setDeleted(resultSet.getBoolean("deleted"));

		return medicine;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement,
			Medicine medicine) throws DaoException {
		try {
			statement.setString(1, medicine.getName());
			statement.setLong(2, medicine.getCountInStore());
			statement.setInt(3, medicine.getCount());
			statement.setInt(4, medicine.getDosageMg());
			statement.setBoolean(5, medicine.isNeedPrescription());
			statement.setBigDecimal(6, medicine.getPrice());
			statement.setBoolean(7, medicine.isDeleted());
		} catch (SQLException e) {
			throw new DaoException("Can't prepare statement tp insert!", e);
		}
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement statement,
			Medicine medicine) throws DaoException {
		try {
			statement.setString(1, medicine.getName());
			statement.setLong(2, medicine.getCountInStore());
			statement.setInt(3, medicine.getCount());
			statement.setInt(4, medicine.getDosageMg());
			statement.setBoolean(5, medicine.isNeedPrescription());
			statement.setBigDecimal(6, medicine.getPrice());
			statement.setBoolean(7, medicine.isDeleted());
			statement.setLong(8, medicine.getId());
		} catch (SQLException e) {
			throw new DaoException("Can't prepare statement for update!", e);
		}
	}
}
