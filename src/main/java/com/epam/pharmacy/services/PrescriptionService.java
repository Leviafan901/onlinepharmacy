package com.epam.pharmacy.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.dao.DaoCreator;
import com.epam.pharmacy.dao.PrescriptionDao;
import com.epam.pharmacy.domain.Prescription;
import com.epam.pharmacy.domain.enumeration.Role;
import com.epam.pharmacy.exceptions.ConnectionException;
import com.epam.pharmacy.exceptions.DaoException;
import com.epam.pharmacy.exceptions.ServiceException;

public class PrescriptionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrescriptionService.class);

	/**
	 * Method, get all medicines from DB for user by his ID
	 * 
	 * @return List<Prescription>
	 * @throws ServiceException
	 */
	public List<Prescription> getPrescriptionList(Long userId, Role userRole) throws ServiceException {
		List<Prescription> prescriptionList = new ArrayList<>();
		try (DaoCreator daoCreator = new DaoCreator()) {
			PrescriptionDao prescriptionDao = daoCreator.getPrescriptionDao();
			prescriptionList = prescriptionDao.getUserPrescriptionsById(userId, userRole);
			LOGGER.info("Find and return all prescriptions of the user = {} role = {} from DB", userId, userRole);
		} catch (DaoException | ConnectionException | SQLException e) {
			throw new ServiceException("Can't return all medicines from DB", e);
		}
		return prescriptionList;
	}
}
