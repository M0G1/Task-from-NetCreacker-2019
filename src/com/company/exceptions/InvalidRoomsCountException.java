package com.company.exceptions;

public class InvalidRoomsCountException extends IllegalArgumentException {
    public InvalidRoomsCountException() {
    }

    public InvalidRoomsCountException(String message) {
        super(message);
    }

    public InvalidRoomsCountException(String message, Throwable cause) {
        super(message, cause);
    }
}
