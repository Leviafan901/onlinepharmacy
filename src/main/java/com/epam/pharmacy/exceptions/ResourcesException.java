package com.epam.pharmacy.exceptions;

public class ResourcesException extends PharmacyException {

    public ResourcesException(String message, Exception cause) {
        super(message, cause);
    }

    public ResourcesException(String message) {
        super(message);
    }
}

