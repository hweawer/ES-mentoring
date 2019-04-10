package com.epam.esm.authentication.exception;

public class DuplicateLoginException extends RuntimeException {
    public DuplicateLoginException() {
    }

    public DuplicateLoginException(String message) {
        super(message);
    }

    public DuplicateLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateLoginException(Throwable cause) {
        super(cause);
    }
}
