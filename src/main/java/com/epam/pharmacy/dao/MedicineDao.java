package com.epam.pharmacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epam.pharmacy.domain.Medicine;
import com.epam.pharmacy.dto.MedicineDto;
import com.epam.pharmacy.exceptions.DaoException;

public class MedicineDao extends AbstractDao<Medicine, MedicineDto> {

	private static final String UPDATE_QUERY = "UPDATE Medicine SET name=?, count_in_store=?, count=?, dosage_mg=?, need_prescription=?, price=? WHERE id = ?";
	private static final String UPDATE_QUERY_DELETE = "UPDATE Medicine SET deleted = true WHERE id = ?";
	private static final String UPDATE_QUERY_MEDICINE_COUNT = "UPDATE Medicine SET count_in_store=? WHERE id = ?";
	private static final String CREATE_QUERY = "INSERT INTO Medicine (name, count_in_store, count, dosage_mg, need_prescription, price, deleted) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String SELECT_QUERY_BY_ID = "SELECT id, name, count_in_store, count, dosage_mg, need_prescription, price, deleted FROM medicine WHERE id = ?";
	private static final String SELECT_QUERY_PRESCRIPTION_INFORMATION = "SELECT need_prescription FROM Medicine WHERE id = ?";
	private static final String SELECT_QUERY_MEDICINE_DTO = "SELECT id, name FROM Medicine WHERE need_prescription = true";
	private static final String SELECT_QUERY = "SELECT id, name, count_in_store, count, dosage_mg, need_prescription, price, deleted FROM Medicine WHERE deleted <> 1";

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
	
	public boolean getPrescriptionInfoFromMedicineById(Long medicineId) throws DaoException {
		try (PreparedStatement statement = createStatement(SELECT_QUERY_PRESCRIPTION_INFORMATION, medicineId)) {
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
				  boolean isNeedPrescription = resultSet.getBoolean("need_prescription");
				  return isNeedPrescription;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Can't execute query!", e);
		}
	}
	
	public List<MedicineDto> getMedicineNameAndIdForPrescriptionCreation() throws DaoException {
		List<MedicineDto> medicineDtoList = new ArrayList<>();
		try (PreparedStatement statement = createStatement(SELECT_QUERY_MEDICINE_DTO)) {
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					medicineDtoList.add(buildDto(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Can't execute query!", e);
		}
		return medicineDtoList;
	}
	
	public boolean deleteMedicineById(Long medicineId) throws DaoException {
		boolean isDeleted = executeUpdateQuery(UPDATE_QUERY_DELETE, medicineId);
		return isDeleted;
	}

	public boolean changeMedicineCountInStoreById(Integer medicineCount, Long medicineId) throws DaoException {
		boolean isChangedCount = executeUpdateQuery(UPDATE_QUERY_MEDICINE_COUNT, medicineCount, medicineId);
	    return isChangedCount;
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
	protected MedicineDto buildDto(ResultSet resultSet) throws SQLException {
		MedicineDto medicineDto = new MedicineDto();
		medicineDto.setMedicineId(resultSet.getLong("id"));
		medicineDto.setMedicineName(resultSet.getString("name"));
		return medicineDto;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement,
			Medicine medicine) throws DaoException {
		try {
			prepareMedicine(statement, medicine);
			statement.setBoolean(7, medicine.isDeleted());
		} catch (SQLException e) {
			throw new DaoException("Can't prepare statement tp insert!", e);
		}
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement statement,
			Medicine medicine) throws DaoException {
		try {
			prepareMedicine(statement, medicine);
			statement.setLong(7, medicine.getId());
		} catch (SQLException e) {
			throw new DaoException("Can't prepare statement for update!", e);
		}
	}
	
	private void prepareMedicine(PreparedStatement statement, Medicine medicine) throws SQLException {
		statement.setString(1, medicine.getName());
		statement.setLong(2, medicine.getCountInStore());
		statement.setInt(3, medicine.getCount());
		statement.setInt(4, medicine.getDosageMg());
		statement.setBoolean(5, medicine.isNeedPrescription());
		statement.setBigDecimal(6, medicine.getPrice());
	}
}
