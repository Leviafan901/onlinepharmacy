package com.epam.pharmacy.services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.dao.DaoCreator;
import com.epam.pharmacy.dao.PrescriptionDao;
import com.epam.pharmacy.dao.RequestDao;
import com.epam.pharmacy.domain.Prescription;
import com.epam.pharmacy.domain.enumeration.Role;
import com.epam.pharmacy.dto.PrescriptionDto;
import com.epam.pharmacy.exceptions.ConnectionException;
import com.epam.pharmacy.exceptions.DaoException;
import com.epam.pharmacy.exceptions.ServiceException;

public class PrescriptionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrescriptionService.class);

	/**
	 * Method, get all medicines from DB for user by his ID
	 * 
	 * @param Long userId, Role userRole
	 * @return List<Prescription>
	 * @throws ServiceException
	 */
	public List<PrescriptionDto> getPrescriptionList(Long userId, Role userRole) throws ServiceException {
		List<PrescriptionDto> prescriptionList = new ArrayList<>();
		try (DaoCreator daoCreator = new DaoCreator()) {
			PrescriptionDao prescriptionDao = daoCreator.getPrescriptionDao();
			prescriptionList = prescriptionDao.getUserPrescriptionsById(userId, userRole);
			Collections.reverse(prescriptionList);
			LOGGER.info("Find and return all prescriptions of the user = {} role = {} from DB", userId, userRole);
		} catch (DaoException | ConnectionException | SQLException e) {
			throw new ServiceException("Can't return all medicines from DB", e);
		}
		return prescriptionList;
	}
	
	/***
	 * Method allows to create new row in the Prescription table
	 * 
	 * @param Prescription newPrescription
	 * @return boolean
	 * @throws ServiceException
	 */
	public boolean createPrescription(Prescription newPrescription) throws ServiceException {	
		Prescription insertedPrescription = null;
		try (DaoCreator daoCreator = new DaoCreator()) {
			try {
				daoCreator.startTransaction();
				PrescriptionDao prescriptionDao = daoCreator.getPrescriptionDao();
				LocalDate nowDate = LocalDate.now();
				newPrescription.setCreationDate(nowDate);
				insertedPrescription = prescriptionDao.insert(newPrescription);
				daoCreator.commitTransaction();
			} catch (DaoException e) {
				daoCreator.rollbackTransaction();
				throw new ServiceException("Can't create new prescription!", e);
			}
		} catch (ConnectionException | SQLException e) {
			throw new ServiceException("Can't create new prescription!", e);
		}
		if (insertedPrescription != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/***
	 * Mehod that change status of Request row to 'approved' and change value of expiration_date in the Prescription table
	 * 
	 * @param Long prescriptionId
	 * @param Long requestId
	 * @param Long expirationDate
	 * @return boolean
	 * @throws ServiceException
	 */
	public boolean extendPrescription(Long prescriptionId, Long requestId, LocalDate expirationDate) throws ServiceException {
		try (DaoCreator daoCreator = new DaoCreator()) {
			try {
				PrescriptionDao prescriptionDao = daoCreator.getPrescriptionDao();
				LocalDate actualExpirationDate = expirationDate.plusMonths(1);
				Date sqlExpirationDate = Date.valueOf(actualExpirationDate);
				daoCreator.startTransaction();
				boolean isExtented = prescriptionDao.extendPrescriptionById(sqlExpirationDate, prescriptionId);
				RequestDao requestDao = daoCreator.getRequestDao();
				boolean isApprovedREquest = requestDao.approveRequestById(requestId);
				daoCreator.commitTransaction();
				if (isExtented && isApprovedREquest) {
					return true;
				} else {
					return false;
				}
			} catch (DaoException e) {
				daoCreator.rollbackTransaction();
				throw new ServiceException("Can't end transaction and extend prescription!", e);
			}
		} catch (ConnectionException | SQLException e) {
			throw new ServiceException("Can't extend prescription!", e);
		}
	}
}
