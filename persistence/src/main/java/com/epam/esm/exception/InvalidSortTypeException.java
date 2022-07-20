package com.epam.esm.exception;

/**
 * Thrown when sort type is not recognised.
 */
public class InvalidSortTypeException extends Exception {
    public InvalidSortTypeException(String message) {
        super(message);
    }
}
