package com.epam.pharmacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.pharmacy.domain.User;
import com.epam.pharmacy.domain.enumeration.Role;
import com.epam.pharmacy.exceptions.DaoException;

public class UserDao extends AbstractDao<User> {

	private static final String UPDATE_QUERY = "UPDATE User SET name=? lastname=? login=? password=? role=? WHERE id = ?";
	private static final String CREATE_QUERY = "INSERT INTO User (name, lastname, login, MD5(password), role) VALUES (?, ?, ?, ?)";
	private static final String SELECT_QUERY_BY_ID = "SELECT name, lastname, login, MD5(password), role FROM User WHERE id = ?";
	private static final String SELECT_QUERY = "SELECT id, name, lastname, login, MD5(password), role FROM User";
	private static final String FIND_BY_LOGIN_PASSWORD = "SELECT * FROM user WHERE login = ? AND password = ?";
	
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
	
	public User getUserByLoginAndPassword(String login, String password) throws DaoException {
		User user = executeQueryForSingleResult(FIND_BY_LOGIN_PASSWORD, login, password);
		return user;
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
	protected void prepareStatementForInsert(PreparedStatement statement, User user)
			throws DaoException {
		try {
			statement.setString(1, user.getName());
			statement.setString(2, user.getLastname());
			statement.setString(3, user.getLogin());
			statement.setString(4, user.getPassword());
			String role = String.valueOf(user.getRole());
			statement.setString(5, role);
		} catch (SQLException e) {
			throw new DaoException("Can't prepare statement to insert!", e);
		}
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement statement, User user)
			throws DaoException {
		try {
			statement.setString(1, user.getName());
			statement.setString(2, user.getLastname());
			statement.setString(3, user.getLogin());
			statement.setString(4, user.getPassword());
			String role = String.valueOf(user.getRole());
			statement.setString(5, role);
			statement.setLong(6, user.getId());
		} catch (SQLException e) {
			throw new DaoException("Can't prepare statement for update!", e);
		}
	}
}
