package com.epam.pharmacy.exceptions;

public class PersistException extends Exception {

	public PersistException(String message, Throwable cause) {
        super(message);
    }

    public PersistException(Throwable cause) {
        super();
    }

	public PersistException(String string) {
		super();
	}
}
