package com.epam.esm.controller.exception;

import java.util.Collections;
import java.util.List;

public class ApiError {

    private String developerMessage;
    private List<String> errors;

    public ApiError(String developerMessage, List<String> errors) {
        super();
        this.developerMessage = developerMessage;
        this.errors = errors;
    }

    public ApiError(String developerMessage, String error) {
        super();
        this.developerMessage = developerMessage;
        errors = Collections.singletonList(error);
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}