package com.epam.esm.exceptionhandling;

/**
 * Collection of custom error codes.
 */
public enum ErrorCodes {
    ID_NOT_FOUND("40401"),
    INVALID_FIELD("40001"),
    INVALID_SORT_VALUE("40002");

    private final String code;

    ErrorCodes(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
