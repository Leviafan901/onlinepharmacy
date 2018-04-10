package com.epam.pharmacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.epam.pharmacy.domain.User;
import com.epam.pharmacy.domain.enumeration.Role;
import com.epam.pharmacy.dto.UserDto;
import com.epam.pharmacy.exceptions.DaoException;

public class UserDao extends AbstractDao<User, UserDto> {

	private static final String UPDATE_QUERY = "UPDATE User SET name=? lastname=? login=? password=? role=? WHERE id = ?";
	private static final String CREATE_QUERY = "INSERT INTO User (name, lastname, login, MD5(password), role) VALUES (?, ?, ?, ?)";
	private static final String SELECT_QUERY_BY_ID = "SELECT name, lastname, login, MD5(password), role FROM User WHERE id = ?";
	private static final String SELECT_QUERY = "SELECT id, name, lastname, login, MD5(password), role FROM User";
	private static final String FIND_BY_LOGIN_PASSWORD = "SELECT * FROM User WHERE login = ? AND password = ?";
	private static final String SELECT_QUERY_USER_DTO = "SELECT id, name, lastname FROM User WHERE role <> 'doctor' and role <> 'admin'";
	
	public UserDao(Connection connection) throws DaoException {
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
	
	public Optional<User> getUserByLoginAndPassword(String login, String password) throws DaoException {
		Optional<User> user = executeQueryForSingleResult(FIND_BY_LOGIN_PASSWORD, login, password);
		return user;
    }
	
	public List<UserDto> getUserNameAndIdForPrescriptionCreation() throws DaoException {
		List<UserDto> userDtoList = executeQueryDto(SELECT_QUERY_USER_DTO);
		return userDtoList;
	}
	
	@Override
	protected User build(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong("id"));
		user.setName(resultSet.getString("name"));
		user.setLastname(resultSet.getString("lastname"));
		user.setLogin(resultSet.getString("login"));
		user.setPassword(resultSet.getString("password"));
		String role = resultSet.getString("role")
				.toUpperCase();
		user.setRole(Role.valueOf(role));
		return user;
	}
	
	@Override
	protected UserDto buildDto(ResultSet resultSet) throws SQLException {
		UserDto userDto = new UserDto();
		userDto.setUserId(resultSet.getLong("id"));
		userDto.setUserName(resultSet.getString("name"));
		userDto.setUserLastname(resultSet.getString("lastname"));
		return userDto;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement, User user)
			throws DaoException {
		try {
			prepareUser(statement, user);
		} catch (SQLException e) {
			throw new DaoException("Can't prepare statement to insert!", e);
		}
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement statement, User user)
			throws DaoException {
		try {
			prepareUser(statement, user);
			statement.setLong(6, user.getId());
		} catch (SQLException e) {
			throw new DaoException("Can't prepare statement for update!", e);
		}
	}
	
	private void prepareUser(PreparedStatement statement, User user) throws SQLException {
		statement.setString(1, user.getName());
		statement.setString(2, user.getLastname());
		statement.setString(3, user.getLogin());
		statement.setString(4, user.getPassword());
		String role = String.valueOf(user.getRole());
		statement.setString(5, role);
	}
}
