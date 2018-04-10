package com.epam.pharmacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.lang.Long;

import com.epam.pharmacy.dto.Dto;
import com.epam.pharmacy.exceptions.DaoException;

/**
 * Class, realize base method's of CRUD operations to DB.
 *
 * @param <T>
 *            object - created form ResultSet or needed to set into DB
 * @param <Long>
 *            PrimaryKey - Long
 */
public abstract class AbstractDao<T extends Identifiable, D extends Dto> implements GenericDao<T> {

	private static final int RESULT_OBJECT = 0;
	private Connection connection;

	public AbstractDao(Connection connection) throws DaoException {
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Method, realize SQL-query of selections row's from DB.
	 * <p/>
	 * SELECT * FROM [Table]
	 */
	public abstract String getSelectQuery();

	/**
	 * Method, realize SQL-query of selections row's from DB by PrimaryKey(id).
	 * <p/>
	 * SELECT * FROM [Table] WHERE id = ?
	 */
	public abstract String getQueryById();

	/**
	 * Method, realize SQL-query insert object's into DB.
	 * <p/>
	 * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
	 */
	public abstract String getCreateQuery();

	/**
	 * Method, realize SQL-query update object's into DB.
	 * <p/>
	 * UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
	 */
	public abstract String getUpdateQuery();

	/**
	 * Method, parse ResultSet and build entity object.
	 */
	protected abstract T build(ResultSet resultSet) throws SQLException;
	
	/**
	 * Method, parse ResultSet and build entity object.
	 */
	protected abstract D buildDto(ResultSet resultSet) throws SQLException;

	/**
	 * Method, determinate insert-query to DB with the field's of argument object.
	 */
	protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws DaoException;

	/**
	 * Method, determinate update-query to DB with the field's of argument object.
	 */
	protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws DaoException;

	public Optional<T> executeQueryForSingleResult(String query, Object... params) throws DaoException {
		List<T> objects = executeQuery(query, params);
		if (objects.isEmpty()) {
			return null;
		}
		Optional<T> result = Optional.of(objects.get(RESULT_OBJECT));
		return result;
	}

	protected List<T> executeQuery(String query, Object... params) throws DaoException {
		List<T> list = new ArrayList<>();
		try (PreparedStatement statement = createStatement(query, params)) {
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					list.add(build(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Can't execute query!", e);
		}
		return list;
	}
	
	protected List<D> executeQueryDto(String query, Object... params) throws DaoException {
		List<D> list = new ArrayList<>();
		try (PreparedStatement statement = createStatement(query, params)) {
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					list.add(buildDto(resultSet));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Can't execute query!", e);
		}
		return list;
	}
	
	protected boolean executeUpdateQuery(String query, Object... params) throws DaoException {
		try (PreparedStatement statement = createStatement(query, params)) {
			int count = statement.executeUpdate();
			boolean updated = count == 1;
			if (!updated) {
				throw new DaoException("On update modify more then 1 record: " + count);
			}
			if (updated) {
				return true;
			}
		} catch (Exception e) {
			throw new DaoException("Can not updatedata from the DB!", e);
		}
		return false;
	}

	protected PreparedStatement createStatement(String query, Object... params) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(query);
		for (int i = 1; i <= params.length; i++) {
			statement.setObject(i, params[i - 1]);
		}
		return statement;
	}

	@Override
	public T insert(T object) throws DaoException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(getCreateQuery(),
				PreparedStatement.RETURN_GENERATED_KEYS)) {
			prepareStatementForInsert(preparedStatement, object);
			int count = preparedStatement.executeUpdate();
			if (count != 1) {
				throw new DaoException("On persist modify more then 1 record: " + count);
			}

			try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
				resultSet.next();
				long id = resultSet.getLong(1);
				object.setId(id);
			}
		} catch (SQLException e) {
			throw new DaoException("Can't insert " + object, e);
		}
		return object;
	}

	@Override
	public Optional<T> getById(Long key) throws DaoException {
		return executeQueryForSingleResult(getQueryById(), key);
	}

	@Override
	public boolean update(T object) throws DaoException {//
		try (PreparedStatement preparedStatement = connection.prepareStatement(getUpdateQuery())) {
			prepareStatementForUpdate(preparedStatement, object);
			int count = preparedStatement.executeUpdate();
			boolean updated = count == 1;
			if (!updated) {
				throw new DaoException("On update modify more then 1 record: " + count);
			}
			if (updated) {
				return true;
			}
		} catch (Exception e) {
			throw new DaoException("Can not updatedata from the DB!", e);
		}
		return false;
	}

	@Override
	public List<T> getAll() throws DaoException {
		return executeQuery(getSelectQuery());
	}
}
