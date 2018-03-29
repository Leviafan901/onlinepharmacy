package com.epam.pharmacy.exceptions;

public class ConnectionException extends PharmacyException {

    public ConnectionException(String message, Throwable cause) {
        super(message);
    }

	public ConnectionException(String string) {
		super(string);
	}
}
