package com.epam.pharmacy.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.dao.DaoCreator;
import com.epam.pharmacy.dao.MedicineDao;
import com.epam.pharmacy.domain.Medicine;
import com.epam.pharmacy.exceptions.ConnectionException;
import com.epam.pharmacy.exceptions.DaoException;
import com.epam.pharmacy.exceptions.ServiceException;

public class MedicineService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MedicineService.class);

	/**
	 * Method, get all medicine from DB
	 * 
	 * @return List<Medicine>
	 * @throws ServiceException
	 */
	public List<Medicine> getMedicineList() throws ServiceException {
		List<Medicine> medicineList = new ArrayList<>();
		try (DaoCreator daoCreator = new DaoCreator()) {
			MedicineDao medicineDao = daoCreator.getMedicineDao();
			medicineList = medicineDao.getAll();
			LOGGER.info("Find and return all medicines from DB");
		} catch (DaoException | ConnectionException | SQLException e) {
			throw new ServiceException("Can't return all medicines from DB", e);
		}
		return medicineList;
	}
}
