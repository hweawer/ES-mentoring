package com.epam.esm.service.exception;

public class BuilderIsNotActive extends RuntimeException {
    public BuilderIsNotActive() {
    }

    public BuilderIsNotActive(String message) {
        super(message);
    }

    public BuilderIsNotActive(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderIsNotActive(Throwable cause) {
        super(cause);
    }
}
