package com.epam.esm.repository.exception;

public class InitializingDataSourceException extends RuntimeException {
    public InitializingDataSourceException() {
    }

    public InitializingDataSourceException(String message) {
        super(message);
    }

    public InitializingDataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitializingDataSourceException(Throwable cause) {
        super(cause);
    }
}
