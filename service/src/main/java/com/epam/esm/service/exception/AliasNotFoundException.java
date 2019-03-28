package com.epam.esm.service.exception;

public class AliasNotFoundException extends RuntimeException{
    public AliasNotFoundException() {
    }

    public AliasNotFoundException(String message) {
        super(message);
    }

    public AliasNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AliasNotFoundException(Throwable cause) {
        super(cause);
    }
}
