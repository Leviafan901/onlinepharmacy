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
import com.epam.pharmacy.dto.PrescriptionDto;
import com.epam.pharmacy.exceptions.DaoException;

public class PrescriptionDao extends AbstractDao<Prescription> {
	
	private static final String UPDATE_QUERY = "UPDATE Prescription SET doctor_id=? user_id=? medicine_id=? creation_date=? expiration_date=? comment=? WHERE id = ?";
	private static final String CREATE_QUERY = "INSERT INTO Prescription (doctor_id, user_id, medicine_id, creation_date, expiration_date, comment) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SELECT_QUERY_BY_ID = "SELECT doctor_id, user_id, medicine_id, creation_date, expiration_date, comment FROM Prescription WHERE id = ?";
	private static final String SELECT_QUERY_DTO_BY_ID = "SELECT medicine_id, creation_date, expiration_date FROM Prescription WHERE user_id = ? AND medicine_id = ? AND ? BETWEEN creation_date AND expiration_date";
	private static final String SELECT_QUERY = "SELECT id, doctor_id, user_id, medicine_id, creation_date, expiration_date, comment FROM Prescription";
	private static final String SELECT_QUERY_FOR_CLIENT = "SELECT id, doctor_id, user_id, medicine_id, creation_date, expiration_date, comment FROM Prescription WHERE user_id = ?";
	private static final String SELECT_QUERY_FOR_DOCTOR = "SELECT id, doctor_id, user_id, medicine_id, creation_date, expiration_date, comment FROM Prescription WHERE doctor_id = ?";
	
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
	
	public List<PrescriptionDto> getPrescriptionsForOrdersValidation(Long clientId, Long medicineId, Date actualDate) throws DaoException {
		List<PrescriptionDto> prescritionDtoList = new ArrayList<>();
		try (PreparedStatement statement = createStatement(SELECT_QUERY_DTO_BY_ID, clientId, medicineId, actualDate)) {
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					prescritionDtoList.add(buildDto(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Can't execute query!", e);
		}
		return prescritionDtoList;
	}
	
	public List<Prescription> getUserPrescriptionsById(Long userId, Role userRole) throws DaoException {
		List<Prescription> prescriptionList = null;
		if (userRole == Role.CLIENT) {
			prescriptionList = executeQuery(SELECT_QUERY_FOR_CLIENT, userId);
		}
		if (userRole == Role.DOCTOR) {
			prescriptionList = executeQuery(SELECT_QUERY_FOR_DOCTOR, userId);
		}
		return prescriptionList;
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
	
	private PrescriptionDto buildDto(ResultSet resultSet) throws SQLException {
		PrescriptionDto prescriptionDto = new PrescriptionDto();
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
	protected void prepareStatementForInsert(PreparedStatement statement,
			Prescription prescription) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement statement,
			Prescription prescription) throws DaoException {
		// TODO Auto-generated method stub
		
	}
}
