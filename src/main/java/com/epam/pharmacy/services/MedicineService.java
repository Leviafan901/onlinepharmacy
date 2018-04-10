package com.epam.pharmacy.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.dao.DaoCreator;
import com.epam.pharmacy.dao.MedicineDao;
import com.epam.pharmacy.domain.Medicine;
import com.epam.pharmacy.dto.MedicineDto;
import com.epam.pharmacy.exceptions.ConnectionException;
import com.epam.pharmacy.exceptions.DaoException;
import com.epam.pharmacy.exceptions.ServiceException;

public class MedicineService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MedicineService.class);

	private static final boolean MEDICINE_DEFAULT_STATUS = false;
	
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
	
	/***
	 * Method, return optional result Medicine object of query by id
	 * 
	 * @param medicineId
	 * @return Optional<Medicine>
	 * @throws ServiceException
	 */
	public Optional<Medicine> getMedicineById(Long medicineId) throws ServiceException {
		Optional<Medicine> medicineToUpdate = Optional.empty();
		try (DaoCreator daoCreator = new DaoCreator()) {
			MedicineDao medicineDao = daoCreator.getMedicineDao();
			medicineToUpdate = medicineDao.getById(medicineId);
		} catch (DaoException | ConnectionException | SQLException e) {
			throw new ServiceException("Can't find and return medicine by id !", e) ;
		}
		return medicineToUpdate;
	}
	
	/***
	 * Method, allows to get List of MedicineDto for further transferring
	 * to the prescription creation form .jsp page
	 * 
	 * @return List<MedicineDto>
	 * @throws ServiceException
	 */
	public List<MedicineDto> getMedicineDtoListForPrescriptionCreation() throws ServiceException {
		List<MedicineDto> medicineDtoList = new ArrayList<>();
		try (DaoCreator daoCreator = new DaoCreator()) {
			MedicineDao medicineDao = daoCreator.getMedicineDao();
			medicineDtoList = medicineDao.getMedicineNameAndIdForPrescriptionCreation();
		} catch (DaoException | ConnectionException | SQLException e) {
			throw new ServiceException("Can't find and return medicine dto list!", e) ;
		}
		return medicineDtoList;
	}
	
	/***
	 * Method that allows to apdate medicine row in DB
	 * 
	 * @param medicineToUpdate
	 * @return boolean
	 * @throws ServiceException
	 */
	public boolean updateMedicine(Medicine medicineToUpdate) throws ServiceException {
		try (DaoCreator daoCreator = new DaoCreator()) {
			try {
				daoCreator.startTransaction();
				MedicineDao medicineDao = daoCreator.getMedicineDao();
				daoCreator.commitTransaction();
				boolean isUpdate = medicineDao.update(medicineToUpdate);
				if (isUpdate) {
					return true;
				} else {
					return false;
				}
			} catch (DaoException e) {
				daoCreator.rollbackTransaction();
				throw new ServiceException("Can't update medicine by id!", e);
			}
		} catch (ConnectionException | SQLException e) {
			throw new ServiceException("Can't update medicine by id!", e);
		}
	}
	
	/***
	 * Method allows to create new row in DB and get inserted Medicine with genereted ID
	 * 
	 * @param newMedicine
	 * @return boolean
	 * @throws ServiceException
	 */
	public boolean createMedicine(Medicine newMedicine) throws ServiceException {
		Medicine insertedMedicine = null;
		try (DaoCreator daoCreator = new DaoCreator()) {
			try {
				daoCreator.startTransaction();
				MedicineDao medicineDao = daoCreator.getMedicineDao();
				newMedicine.setDeleted(MEDICINE_DEFAULT_STATUS);
				insertedMedicine = medicineDao.insert(newMedicine);
				daoCreator.commitTransaction();				
			} catch (DaoException e) {
				daoCreator.rollbackTransaction();
				throw new ServiceException("Can't create new medicine!", e);
			}
		} catch (ConnectionException | SQLException e) {
			throw new ServiceException("Can't create new medicine!", e);
		}
		if (insertedMedicine != null) {
			return true;
		} else {
			return false;
		}
	}
}
