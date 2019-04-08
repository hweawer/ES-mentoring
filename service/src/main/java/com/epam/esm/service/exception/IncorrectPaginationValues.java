package com.epam.esm.service.exception;

public class IncorrectPaginationValues extends RuntimeException {
    public IncorrectPaginationValues() {
    }

    public IncorrectPaginationValues(String message) {
        super(message);
    }

    public IncorrectPaginationValues(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectPaginationValues(Throwable cause) {
        super(cause);
    }
}
