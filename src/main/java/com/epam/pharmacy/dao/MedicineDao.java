package com.epam.pharmacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.epam.pharmacy.domain.Medicine;
import com.epam.pharmacy.exceptions.PersistException;

public class MedicineDao extends AbstractDao<Medicine, Long> {

	private static final String DELETE_QUERY = "DELETE FROM Medicine WHERE id = ?;";
	private static final String UPDATE_QUERY = "UPDATE Medicine SET name=? count_in_store=? count=? dosage_mg=? picture=? need_prescription=? price=? WHERE id = ?;";
	private static final String CREATE_QUERY = "INSERT INTO Medicine (name, count_in_store, count, dosage_mg, picture, need_prescription, price) VALUES (?, ?, ?, ?, ?, ?, ?);";
	private static final String SELECT_QUERY_BY_ID = "SELECT name, count_in_store, count, dosage_mg, picture, need_prescription, price FROM Medicine WHERE id = ?;";
	private static final String SELECT_QUERY = "SELECT id, name, count_in_store, count, dosage_mg, picture, need_prescription, price FROM Medicine";

	public MedicineDao(Connection connection) throws PersistException {
		super(connection);
	}

	@Override
	public String getSelectQuery() {
		return SELECT_QUERY;
	}

	@Override
	public String getQueryByPK() {
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
	public String getDeleteQuery() {
		return DELETE_QUERY;
	}

	@Override
	protected List<Medicine> parseResultSet(ResultSet resultSet) throws PersistException {
		LinkedList<Medicine> result = new LinkedList<Medicine>();
		try {
			while (resultSet.next()) {
				Medicine medicine = new Medicine();
				medicine.setId(resultSet.getLong("id"));
				medicine.setName(resultSet.getString("name"));
				medicine.setCountInStore(resultSet.getLong("count_in_store"));
				medicine.setCount(resultSet.getInt("count"));
				medicine.setDosageMg(resultSet.getInt("dosage_mg"));
				medicine.setNeedPrescription(resultSet.getBoolean("need_prescription"));
				medicine.setPrice(resultSet.getBigDecimal("price"));
				result.add(medicine);
			}
		} catch (Exception e) {
			throw new PersistException(e);
		}
		return result;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement, Medicine medicine) throws PersistException {
		try {
			statement.setString(1, medicine.getName());
			statement.setLong(2, medicine.getCountInStore());
			statement.setInt(3, medicine.getCount());
			statement.setInt(4, medicine.getDosageMg());
			statement.setBoolean(5, medicine.isNeedPrescription());
			statement.setBigDecimal(6, medicine.getPrice());
		} catch (Exception e) {
			throw new PersistException(e);
		}
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement statement, Medicine medicine) throws PersistException {
		try {
			statement.setString(1, medicine.getName());
			statement.setLong(2, medicine.getCountInStore());
			statement.setInt(3, medicine.getCount());
			statement.setInt(4, medicine.getDosageMg());
			statement.setBoolean(5, medicine.isNeedPrescription());
			statement.setBigDecimal(6, medicine.getPrice());
			statement.setLong(7, medicine.getId());
		} catch (Exception e) {
			throw new PersistException(e);
		}
	}
}
