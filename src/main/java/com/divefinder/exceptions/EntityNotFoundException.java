package com.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, int id) {
        super(message + id);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
