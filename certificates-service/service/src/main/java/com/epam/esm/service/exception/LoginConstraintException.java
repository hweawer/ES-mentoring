package com.epam.esm.service.exception;

public class LoginConstraintException extends RuntimeException {
    public LoginConstraintException() {
    }

    public LoginConstraintException(String message) {
        super(message);
    }

    public LoginConstraintException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginConstraintException(Throwable cause) {
        super(cause);
    }
}
