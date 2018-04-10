package com.epam.pharmacy.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epam.pharmacy.domain.Prescription;
import com.epam.pharmacy.domain.enumeration.Role;
import com.epam.pharmacy.dto.OrderPrescriptionInfo;
import com.epam.pharmacy.dto.PrescriptionDto;
import com.epam.pharmacy.exceptions.DaoException;

public class PrescriptionDao extends AbstractDao<Prescription, PrescriptionDto> {
	
	private static final String UPDATE_QUERY = "UPDATE Prescription SET doctor_id=? user_id=? medicine_id=? creation_date=? expiration_date=? comment=? WHERE id = ?";
	private static final String UPDATE_QUERY_EXTENTION = "UPDATE Prescription SET expiration_date=? WHERE id = ?";
	private static final String CREATE_QUERY = "INSERT INTO Prescription (doctor_id, user_id, medicine_id, creation_date, expiration_date, comment) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SELECT_QUERY_BY_ID = "SELECT doctor_id, user_id, medicine_id, creation_date, expiration_date, comment FROM Prescription WHERE id = ?";
	private static final String SELECT_QUERY_DTO_BY_ID = "SELECT medicine_id, creation_date, expiration_date FROM Prescription WHERE user_id = ? AND medicine_id = ? AND ? BETWEEN creation_date AND expiration_date";
	private static final String SELECT_QUERY = "SELECT id, doctor_id, user_id, medicine_id, creation_date, expiration_date, comment FROM Prescription";
	private static final String SELECT_QUERY_FOR_CLIENT = "SELECT p.id, p.doctor_id, u.name AS user_name, u.lastname AS user_lastname, m.name AS medicine_name, p.creation_date, p.expiration_date, p.comment FROM prescription p, user u, medicine m WHERE p.medicine_id = m.id AND u.id = p.doctor_id AND p.user_id = ? ORDER BY p.creation_date";
	private static final String SELECT_QUERY_FOR_DOCTOR = "SELECT p.id, p.doctor_id, u.name AS user_name, u.lastname AS user_lastname, m.name AS medicine_name, p.creation_date, p.expiration_date, p.comment FROM prescription p, user u, medicine m WHERE p.medicine_id = m.id AND u.id = p.user_id AND p.doctor_id = ? ORDER BY p.creation_date";
	
	public PrescriptionDao(Connection connection) throws DaoException {
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
	
	public List<OrderPrescriptionInfo> getPrescriptionsForOrdersValidation(Long clientId, Long medicineId, Date actualDate) throws DaoException {
		List<OrderPrescriptionInfo> prescritionDtoList = new ArrayList<>();
		try (PreparedStatement statement = createStatement(SELECT_QUERY_DTO_BY_ID, clientId, medicineId, actualDate)) {
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					prescritionDtoList.add(buildOrderPrescriptionInfo(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Can't execute query!", e);
		}
		return prescritionDtoList;
	}
	
	public List<PrescriptionDto> getUserPrescriptionsById(Long userId, Role userRole) throws DaoException {
		List<PrescriptionDto> prescriptionDtoList = new ArrayList<>();
		if (userRole == Role.CLIENT) {
			prescriptionDtoList = executeQueryDto(SELECT_QUERY_FOR_CLIENT, userId);
		}
		if (userRole == Role.DOCTOR) {
			prescriptionDtoList = executeQueryDto(SELECT_QUERY_FOR_DOCTOR, userId);
		}
		return prescriptionDtoList;
	}
	
    public boolean extendPrescriptionById(Date newExpirationDate,
    		Long prescriptionId) throws DaoException {
    	boolean isExtented = executeUpdateQuery(UPDATE_QUERY_EXTENTION, newExpirationDate, prescriptionId);
    	return isExtented;
    }
	
	@Override
	protected Prescription build(ResultSet resultSet) throws SQLException {
		Prescription prescription = new Prescription();
		prescription.setId(resultSet.getLong("id"));
		prescription.setDoctorId(resultSet.getLong("doctor_id"));
		prescription.setUserId(resultSet.getLong("user_id"));
		prescription.setMedicineId(resultSet.getLong("medicine_id"));
		Date creationSqlDate = resultSet.getDate("creation_date");//
		LocalDate creationLocalDate = creationSqlDate.toLocalDate();
		prescription.setCreationDate(creationLocalDate);
		Date expirationSqlDate = resultSet.getDate("expiration_date");//
		LocalDate expirationLocalDate = expirationSqlDate.toLocalDate();
		prescription.setExpirationDate(expirationLocalDate);
		prescription.setComment(resultSet.getString("comment"));
		return prescription;
	}
	
	private OrderPrescriptionInfo buildOrderPrescriptionInfo(ResultSet resultSet) throws SQLException {
		OrderPrescriptionInfo prescriptionDto = new OrderPrescriptionInfo();
		prescriptionDto.setMedicineId(resultSet.getLong("medicine_id"));
		Date creationSqlDate = resultSet.getDate("creation_date");//
		LocalDate creationLocalDate = creationSqlDate.toLocalDate();
		prescriptionDto.setCreationDate(creationLocalDate);
		Date expirationSqlDate = resultSet.getDate("expiration_date");//
		LocalDate expirationLocalDate = expirationSqlDate.toLocalDate();
		prescriptionDto.setExpirationDate(expirationLocalDate);
		return prescriptionDto;
	}
	
	@Override
	protected PrescriptionDto buildDto(ResultSet resultSet) throws SQLException {
		PrescriptionDto prescriptionDto = new PrescriptionDto();
		prescriptionDto.setUserName(resultSet.getString("user_name"));
		prescriptionDto.setUserLastname(resultSet.getString("user_lastname"));
		prescriptionDto.setMedicineName(resultSet.getString("medicine_name"));
		prescriptionDto.setId(resultSet.getLong("id"));
		prescriptionDto.setDoctorId(resultSet.getLong("doctor_id"));
		Date creationSqlDate = resultSet.getDate("creation_date");//
		LocalDate creationLocalDate = creationSqlDate.toLocalDate();
		prescriptionDto.setCreationDate(creationLocalDate);
		Date expirationSqlDate = resultSet.getDate("expiration_date");//
		LocalDate expirationLocalDate = expirationSqlDate.toLocalDate();
		prescriptionDto.setExpirationDate(expirationLocalDate);
		prescriptionDto.setComment(resultSet.getString("comment"));
		return prescriptionDto;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement, Prescription prescription)
			throws DaoException {
		try {
			preparePrescription(statement, prescription);
		} catch (SQLException e) {
			throw new DaoException("Can't prepare statement to insert!", e);
		}
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement statement, Prescription prescription)
			throws DaoException {
		try {
			preparePrescription(statement, prescription);
			statement.setLong(7, prescription.getId());
		} catch (SQLException e) {
			throw new DaoException("Can't prepare statement to insert!", e);
		}
	}
	
	private void preparePrescription(PreparedStatement statement, Prescription prescription)
			throws SQLException {
		statement.setLong(1, prescription.getDoctorId());
		statement.setLong(2, prescription.getUserId());
		statement.setLong(3, prescription.getMedicineId());
		Date sqlCreationDate = Date.valueOf(prescription.getCreationDate());
		statement.setDate(4, sqlCreationDate);
		Date sqlExpirationDate = Date.valueOf(prescription.getExpirationDate());
		statement.setDate(5, sqlExpirationDate);
		statement.setString(6, prescription.getComment());
	}
}
