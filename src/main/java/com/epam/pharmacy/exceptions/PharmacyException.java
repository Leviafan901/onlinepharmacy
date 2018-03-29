package com.epam.pharmacy.exceptions;

public class PharmacyException extends Exception {

	public PharmacyException(String message, Throwable cause) {
        super(message, cause);
    }

	public PharmacyException(String message) {
		super(message);
	}
}
