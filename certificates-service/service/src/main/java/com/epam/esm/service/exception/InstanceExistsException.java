package com.epam.esm.service.exception;

public class InstanceExistsException extends RuntimeException {
    public InstanceExistsException() {
    }

    public InstanceExistsException(String message) {
        super(message);
    }

    public InstanceExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstanceExistsException(Throwable cause) {
        super(cause);
    }
}
