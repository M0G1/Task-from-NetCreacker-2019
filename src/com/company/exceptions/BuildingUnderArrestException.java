package com.company.exceptions;

public class BuildingUnderArrestException extends Exception {
    public BuildingUnderArrestException() {
        super();
    }

    public BuildingUnderArrestException(String message) {
        super(message);
    }

    public BuildingUnderArrestException(String message, Throwable cause) {
        super(message, cause);
    }
}
