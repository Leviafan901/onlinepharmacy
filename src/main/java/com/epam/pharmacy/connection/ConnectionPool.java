package com.epam.pharmacy.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.exceptions.ConnectionException;

public class ConnectionPool {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class);

	private static final String PASSWORD = "password";
	private static final String USER = "user";
	private static final String DB_URL = "DBUrl";
	private static final String DRIVER = "driver";
	private static final String PROPERTIES_FILE = "prop.properties";
	private static ConnectionPool instance = null;
	private static AtomicBoolean isInstanceAvailable = new AtomicBoolean(true);
	private static Lock locker = new ReentrantLock();
	private final static int CONNECTION_POOL_SIZE = 10;
	private final static int MAX_AWAIT_TIME = 10;
	private ResourcesQueue<Connection> connections = null;

	private ConnectionPool() throws ConnectionException {
		init();
	}

	public static ConnectionPool getInstance() throws ConnectionException {
		boolean isAvailable = isInstanceAvailable.get();
		if (isAvailable) {
			locker.lock();
			try {
				if (instance == null) {
					instance = new ConnectionPool();
					isInstanceAvailable.set(false);
				}
			} finally {
				locker.unlock();
			}
		}
		return instance;
	}

	private void init() throws ConnectionException {
		connections = new ResourcesQueue<Connection>(CONNECTION_POOL_SIZE, MAX_AWAIT_TIME);
		try {
			while (connections.size() < CONNECTION_POOL_SIZE) {
				Class.forName(getProperties().getProperty(DRIVER));
				Connection connection = DriverManager.getConnection(
	    	    		getProperties().getProperty(DB_URL),
	    	    		getProperties().getProperty(USER),
	    	    		getProperties().getProperty(PASSWORD));
				connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);//
				connections.addResource(connection);
			}
		} catch (SQLException | ClassNotFoundException e) {
			throw new ConnectionException("Error: can't register driver!", e);
		}
	}
	
	private Properties getProperties() throws ConnectionException {
    	Properties prop = null;
    	try {
    		prop = new Properties();
    		prop.load(getClass().getClassLoader()
					.getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
        	throw new ConnectionException("Error: can't get properties file!", e);
        }
            return prop;
    }

	public Connection getConnection() {
		locker.lock();
		try {
			return connections.takeResource();
		} catch (Exception e) {
			throw new RuntimeException("Error in a getConnection() , don't avalible connect", e);
		} finally {
			locker.unlock();
		}
	}

	public void returnConnection(Connection connection) {
		locker.lock();
		try {
			connections.returnResource(connection);
		} finally {
			locker.unlock();
		}
	}
	
	public void closeAllConnections() {
        LOGGER.info("Close all connection in connection pool");
        for (Connection connection : connections.getResources()) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("Can't close all connection in connection pool", e);
            }
        }
    }

    public int size() {
        return connections.size();
    }
}
