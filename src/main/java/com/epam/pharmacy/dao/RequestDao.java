package com.epam.pharmacy.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.epam.pharmacy.domain.Request;
import com.epam.pharmacy.domain.enumeration.RequestStatus;
import com.epam.pharmacy.dto.RequestDto;
import com.epam.pharmacy.exceptions.DaoException;

public class RequestDao extends AbstractDao<Request, RequestDto> {

	private static final String UPDATE_QUERY = "UPDATE Request SET doctor_id=? prescription_id=? status=? WHERE id = ?";
	private static final String UPDATE_QUERY_REJECT_REQUEST = "UPDATE Request SET status='rejected' WHERE id = ?";
	private static final String UPDATE_QUERY_APPROVED_REQUEST = "UPDATE Request SET status='approved' WHERE id = ?";
	private static final String CREATE_QUERY = "INSERT INTO Request (doctor_id, prescription_id, status) VALUES (?, ?, ?)";
	private static final String SELECT_QUERY_BY_ID = "SELECT doctor_id, prescription_id, status FROM Request WHERE id = ?";
	private static final String SELECT_QUERY_REQUEST_DTO_BY_CLIENT_ID = "SELECT r.id AS request_id, r.doctor_id, r.status, pres_info.prescription_id, creation_date, expiration_date, user_name, user_lastname FROM (SELECT u.id, p.user_id, u.name AS user_name, u.lastname AS user_lastname, p.id AS prescription_id, p.creation_date, p.expiration_date FROM prescription p" + 
			                                                           " LEFT JOIN pharmacy.`user` u ON u.id = p.doctor_id) AS pres_info INNER JOIN request r ON r.prescription_id = pres_info.prescription_id WHERE pres_info.user_id = ? ORDER BY r.id";
	private static final String SELECT_QUERY_REQUEST_DTO_BY_DOCTOR_ID = "SELECT r.id AS request_id, r.doctor_id, r.status, pres_info.prescription_id, creation_date, expiration_date, user_name, user_lastname FROM (SELECT u.id, u.name AS user_name, u.lastname AS user_lastname, p.id AS prescription_id, p.creation_date, p.expiration_date FROM prescription p" + 
                                                                        " LEFT JOIN pharmacy.`user` u ON p.user_id = u.id) AS pres_info INNER JOIN request r ON r.prescription_id = pres_info.prescription_id WHERE r.doctor_id = ? ORDER BY r.id";
	private static final String SELECT_QUERY = "SELECT id, doctor_id, prescription_id, status FROM Request";
	
	public RequestDao(Connection connection) throws DaoException {
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
	
	public List<RequestDto> getAllClientRequestDtoById(Long userId) throws DaoException {
		List<RequestDto> requestDtoList = executeQueryDto(SELECT_QUERY_REQUEST_DTO_BY_CLIENT_ID, userId);
		return requestDtoList;
	}
	
	public List<RequestDto> getAllDoctorRequestDtoById(Long userId) throws DaoException {
		List<RequestDto> requestDtoList = executeQueryDto(SELECT_QUERY_REQUEST_DTO_BY_DOCTOR_ID, userId);
		return requestDtoList;
	}
	
	public boolean rejectRequestById(Long requestId) throws DaoException {
		boolean isRejected = executeUpdateQuery(UPDATE_QUERY_REJECT_REQUEST, requestId);
		return isRejected;
	}
	
	public boolean approveRequestById(Long requestId) throws DaoException {
		boolean isApproved = executeUpdateQuery(UPDATE_QUERY_APPROVED_REQUEST, requestId);
		return isApproved;
	}

	@Override
	protected RequestDto buildDto(ResultSet resultSet) throws SQLException {
		RequestDto requestDto = new RequestDto();
		requestDto.setDoctorId(resultSet.getLong("doctor_id"));
		requestDto.setRequestId(resultSet.getLong("request_id"));
		requestDto.setPrescriptionId(resultSet.getLong("prescription_id"));
		String status = resultSet.getString("status")
				.toUpperCase();
		requestDto.setStatus(RequestStatus.valueOf(status));
		Date creationSqlDate = resultSet.getDate("creation_date");
		LocalDate creationLocalDate = creationSqlDate.toLocalDate();
		requestDto.setCreationDate(creationLocalDate);
		Date expirationSqlDate = resultSet.getDate("expiration_date");
		LocalDate expirationLocalDate = expirationSqlDate.toLocalDate();
		requestDto.setExpirationDate(expirationLocalDate);
		requestDto.setUserName(resultSet.getString("user_name"));
		requestDto.setUserLastname(resultSet.getString("user_lastname"));
		return requestDto;
	}

	@Override
	protected Request build(ResultSet resultSet) throws SQLException {
		Request request = new Request();
		request.setId(resultSet.getLong("id"));
		request.setDoctorId(resultSet.getLong("doctor_id"));
		request.setPrescriptionId(resultSet.getLong("prescription_id"));
		String status = resultSet.getString("status")
				.toUpperCase();
		request.setStatus(RequestStatus.valueOf(status));
		return request;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement,
			Request request) throws DaoException {
		try {
			prepareRequest(statement, request);
		} catch (SQLException e) {
			throw new DaoException("Can't prepare statement to insert!", e);
		}
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement statement,
			Request request) throws DaoException {
		try {
		    prepareRequest(statement, request);
		    statement.setLong(4, request.getId());
		} catch (SQLException e) {
			throw new DaoException("Can't prepare statement to insert!", e);
		}
		
	}
	
	private void prepareRequest(PreparedStatement statement, Request request) throws SQLException {
		statement.setLong(1, request.getDoctorId());
		statement.setLong(2, request.getPrescriptionId());
		String status = String.valueOf(request.getStatus());
		statement.setString(3, status);
	}
}
