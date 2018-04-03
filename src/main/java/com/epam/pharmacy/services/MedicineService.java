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
	
	/***
	 * Method that allow's to delete(mean change status of medicine 'delete' from false to true)
	 * medicine from DB
	 * 
	 * @param medicineId
	 * @return boolean
	 * @throws ServiceException
	 */
	public boolean deleteMedicine(Long medicineId) throws ServiceException {
		try (DaoCreator daoCreator = new DaoCreator()) {
			MedicineDao medicineDao = daoCreator.getMedicineDao();
			boolean isDeleted = medicineDao.deleteMedicineById(medicineId);
			return isDeleted;
		} catch (DaoException | ConnectionException | SQLException e) {
			throw new ServiceException("Can't delete medicine from DB", e);
		}
	}
	
	public Medicine getMedicineById(Long medicineId) throws ServiceException {
		Medicine medicineToUpdate = null;
		try (DaoCreator daoCreator = new DaoCreator()) {
			MedicineDao medicineDao = daoCreator.getMedicineDao();
			medicineToUpdate = medicineDao.getById(medicineId);
		} catch (DaoException | ConnectionException | SQLException e) {
			throw new ServiceException("Can't find and return medicine by id !", e) ;
		}
		return medicineToUpdate;
	}
}
