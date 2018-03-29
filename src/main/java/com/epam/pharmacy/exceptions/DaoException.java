package com.epam.pharmacy.exceptions;

public class DaoException extends PharmacyException {

	public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

	public DaoException(String message) {
		super(message);
	}
}
