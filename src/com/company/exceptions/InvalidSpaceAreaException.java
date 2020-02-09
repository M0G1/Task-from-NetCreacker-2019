package com.company.exceptions;

public class InvalidSpaceAreaException extends IllegalArgumentException {
    public InvalidSpaceAreaException() {
    }

    public InvalidSpaceAreaException(String message) {
        super(message);
    }

    public InvalidSpaceAreaException(String message, Throwable cause) {
        super(message, cause);
    }
}
