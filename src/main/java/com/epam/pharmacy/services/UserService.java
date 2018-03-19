package com.epam.pharmacy.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.dao.DaoCreator;
import com.epam.pharmacy.dao.UserDao;
import com.epam.pharmacy.domain.User;
import com.epam.pharmacy.exceptions.ServiceException;

public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	/**
	 * Method, search user by login and password
	 *
	 * @param login
	 *            - user login
	 * @param password
	 *            - user password
	 * @return - specific user
	 * @throws ServiceException
	 */
	public User findUserByLoginAndPassword(String login, String password)
			throws ServiceException {
		User user = null;
		try (DaoCreator daoFactory = new DaoCreator()) {
			UserDao userDao = daoFactory.getUserDao();
			user = userDao.getUsersByLoginAndPassword(login, password);
            userDao.close();
			LOGGER.info("Find customer by login and password where login/password equals: {} ****", login);
		} catch (Exception e) {
			throw new ServiceException("can't find by login and password customer", e);
		}
		return user;
	}
}
