package com.epam.esm.service.exception;

public class SnapshotOrderedException extends RuntimeException {
    public SnapshotOrderedException() {
    }

    public SnapshotOrderedException(String message) {
        super(message);
    }

    public SnapshotOrderedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SnapshotOrderedException(Throwable cause) {
        super(cause);
    }
}
