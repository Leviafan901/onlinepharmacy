package com.epam.pharmacy.exceptions;

public class ConnectionException extends Exception {

    public ConnectionException(String message, Throwable cause) {
        super(message);
    }

    public ConnectionException(Throwable cause) {
        super();
    }

	public ConnectionException(String string) {
		super();
	}
}
