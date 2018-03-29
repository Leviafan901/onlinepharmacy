package com.epam.pharmacy.exceptions;

public class ServiceException extends PharmacyException {

    public ServiceException(String message, Exception cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }
}
