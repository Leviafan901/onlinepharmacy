package com.epam.pharmacy.services;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.dao.DaoCreator;
import com.epam.pharmacy.dao.UserDao;
import com.epam.pharmacy.domain.User;
import com.epam.pharmacy.dto.UserDto;
import com.epam.pharmacy.exceptions.ConnectionException;
import com.epam.pharmacy.exceptions.DaoException;
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
	 * @return - Optional<User>
	 * @throws ServiceException
	 */
	public Optional<User> findUserByLoginAndPassword(String login, String password)
			throws ServiceException {
		Optional<User> user = Optional.empty();
		try (DaoCreator daoCreator = new DaoCreator()) {
			UserDao userDao = daoCreator.getUserDao();
			user = userDao.getUserByLoginAndPassword(login, password);
			LOGGER.info("Find customer by login and password where login/password equals: {} ****", login);
		} catch (DaoException | ConnectionException | SQLException e) {
			throw new ServiceException("can't find by login and password customer", e);
		}
		return user;
	}
	
	/***
	 * Method allows to get User information for prescription creation form in the .jsp page
	 * 
	 * @return List<UserDto>
	 * @throws ServiceException
	 */
	public List<UserDto> getUserDtoListForPrescriptionCreation() throws ServiceException {
		List<UserDto> userDtoList = new ArrayList<>();
		try (DaoCreator daoCreator = new DaoCreator()) {
			UserDao userDao = daoCreator.getUserDao();
			userDtoList = userDao.getUserNameAndIdForPrescriptionCreation();
		} catch (DaoException | ConnectionException | SQLException e) {
			throw new ServiceException("Can't find and return user dto list!", e) ;
		}
		return userDtoList;
	}
}
