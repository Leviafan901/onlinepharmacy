package com.epam.pharmacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.exceptions.PersistException;

/**
 * Class, realize base method's of CRUD operations to DB.
 *
 * @param <T>  object - created form ResultSet or needed to set into DB
 * @param <PK> PrimaryKey - integer
 */
public abstract class AbstractDao<T extends Identified<PK>, PK extends Long> implements GenericDao<T, PK> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDao.class);
	
    private Connection connection;
    private PreparedStatement selectStatement = null;
    private PreparedStatement createStatement = null;
    private PreparedStatement deleteStatement = null;
    private PreparedStatement queryByPKStatement = null;
    private PreparedStatement updateStatement = null;
    
    public AbstractDao(Connection connection) throws PersistException {
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
    public abstract String getQueryByPK();

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
     * Method, realize SQL-query delete object's by id.
     * <p/>
     * DELETE FROM [Table] WHERE id= ?;
     */
    public abstract String getDeleteQuery();

    /**
     * Method, parse ResultSet and return List of entities object's.
     */
    protected abstract List<T> parseResultSet(ResultSet resultSet) throws PersistException;

    /**
     * Method, determinate insert-query to DB with the field's of argument object.
     */
    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws PersistException;

    /**
     * Method, determinate update-query to DB with the field's of argument object.
     */
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws PersistException;

    @Override
    public T insert(T object) throws PersistException {
    	ResultSet resultSet = null;
        try {
        	if(this.createStatement == null) {
        	    this.createStatement = connection.prepareStatement(getCreateQuery(), PreparedStatement.RETURN_GENERATED_KEYS);
        	}
            prepareStatementForInsert(createStatement, object);
            int count = createStatement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On persist modify more then 1 record: " + count);
            }
            resultSet = createStatement.getGeneratedKeys();
            Long key = null;
            if (resultSet.next()) {
            	key = resultSet.getLong(1);
            }
            object.setId((PK) key);
                
        } catch (Exception e) {
            throw new PersistException("Can't persist data!", e);
        } finally {
        	if (resultSet != null) {
        		try {
        			resultSet.close();
        		} catch (Exception e) {
        			LOGGER.warn("Can't close resultSet!", e);
        		}
             }
        }
        return object;
    }

    @Override
    public T getByPK(Long key) throws PersistException {
        List<T> list;
        ResultSet resultSet = null;
        try {
        	if (this.queryByPKStatement == null) {
        		this.queryByPKStatement = connection.prepareStatement(getQueryByPK());
        	}
            queryByPKStatement.setLong(1, key);
            resultSet = queryByPKStatement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (Exception e) {
            throw new PersistException(e);
        } finally {
        	if (resultSet != null) {
        		try {
        			resultSet.close();
        		} catch (Exception e) {
        			LOGGER.warn("Can't close resultSet!", e);
        		}
             }
        }
        boolean isNullList = list == null;
        boolean isEmptyList = list.size() == 0;
        if (isNullList || isEmptyList) {
            throw new PersistException("Record with PK = " + key + " not found.");
        }
        boolean isBiggerOne = list.size() > 1;
        if (isBiggerOne) {
            throw new PersistException("Received more than one record.");
        }
        return list.iterator().next();
    }

    @Override
    public void update(T object) throws PersistException {
        try {
        	if (this.updateStatement == null) {
        		this.updateStatement = connection.prepareStatement(getUpdateQuery());
        	}
            prepareStatementForUpdate(updateStatement, object);
            int count = updateStatement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On update modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException("Can not updatedata from the DB!", e);
        }
    }

    @Override
    public void delete(T object) throws PersistException {
        try {
        	if (this.deleteStatement == null) {
        		this.deleteStatement = connection.prepareStatement(getDeleteQuery());
        	}
            deleteStatement.setObject(1, object.getId());

            int count = deleteStatement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On delete modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException("Can not delete data from the DB!", e);
        }
    }

    @Override
    public List<T> getAll() throws PersistException {
        List<T> list;
        ResultSet resultSet  = null;
        try {
            if (this.selectStatement == null) {
            	this.selectStatement = connection.prepareStatement(getSelectQuery());
            }
            resultSet = selectStatement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (Exception e) {
            throw new PersistException("Error: can't get all rows from DB! Object.getAll()", e);
        } finally {
        	if (resultSet != null) {
        		try {
        			resultSet.close();
        		} catch (Exception e) {
        			LOGGER.warn("Can't close resultSet!", e);
        		}
             }
        }
        return list;
    }
    
    @Override
    public void close() throws PersistException {
    	List<PreparedStatement> listStat = new ArrayList<>();
    	listStat.add(this.createStatement);
    	listStat.add(this.selectStatement);
    	listStat.add(this.deleteStatement);
    	listStat.add(this.updateStatement);
    	listStat.add(this.queryByPKStatement);
    	
    	PreparedStatement statement;
    	Exception e = null;
    	for (Iterator<PreparedStatement> iter = listStat.iterator(); iter.hasNext(); ) {
    	    statement = iter.next();
    	    if (statement != null) {
    		    try {
    			    statement.close();
    		        } catch (Exception exp) {
    		    	    e = exp;
    	            }
    	        }
    	    }
    	if (e != null) {
		     throw new PersistException(e);
    	}
    }
}
