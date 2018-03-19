package com.epam.pharmacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.epam.pharmacy.domain.User;
import com.epam.pharmacy.domain.enumeration.Role;
import com.epam.pharmacy.exceptions.PersistException;

public class UserDao extends AbstractDao<User, Long> {

	private static final String DELETE_QUERY = "DELETE FROM User WHERE id = ?;";
	private static final String UPDATE_QUERY = "UPDATE User SET name=? lastname=? login=? password=? roles=? WHERE id = ?;";
	private static final String CREATE_QUERY = "INSERT INTO User (name, lastname, login, password, roles) VALUES (?, ?, ?, ?, ?);";
	private static final String SELECT_QUERY_BY_ID = "SELECT name, lastname, login, MD5(password), roles FROM User WHERE id = ?;";
	private static final String SELECT_QUERY = "SELECT id, name, lastname, login, MD5(password), roles FROM User;";
	private static final String FIND_BY_LOGIN_PASSWORD = "SELECT * FROM user WHERE login = ? AND password = ?;";
	
	public UserDao(Connection connection) throws PersistException {
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
	
	public User getUsersByLoginAndPassword(String login, String password)
			throws PersistException {
		User user = null;
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_LOGIN_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user = parseUser(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new PersistException("Can't get by login and password ", e);
        }
        return user;
    }

	@Override
	protected List<User> parseResultSet(ResultSet resultSet) throws PersistException {
		LinkedList<User> result = new LinkedList<User>();
		try {
			while (resultSet.next()) {
				User user = parseUser(resultSet);
				result.add(user);
			}
		} catch (Exception e) {
			throw new PersistException(e);
		}
		return result;
	}

	private User parseUser(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong("id"));
		user.setName(resultSet.getString("name"));
		user.setLastname(resultSet.getString("lastname"));
		user.setLogin(resultSet.getString("login"));
		user.setPassword(resultSet.getString("password"));
		String role = resultSet.getString("roles")
				.toUpperCase();
		user.setRole(Role.valueOf(role));
		return user;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement, User user)
			throws PersistException {
		try {
			statement.setString(1, user.getName());
			statement.setString(2, user.getLastname());
			statement.setString(3, user.getLogin());
			statement.setString(4, user.getPassword());
			String role = String.valueOf(user.getRole());
			statement.setString(5, role);
		} catch (Exception e) {
			throw new PersistException(e);
		}
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement statement, User user)
			throws PersistException {
		try {
			statement.setString(1, user.getName());
			statement.setString(2, user.getLastname());
			statement.setString(3, user.getLogin());
			statement.setString(4, user.getPassword());
			String role = String.valueOf(user.getRole());
			statement.setString(5, role);
			statement.setLong(7, user.getId());
		} catch (Exception e) {
			throw new PersistException(e);
		}
	}
}
