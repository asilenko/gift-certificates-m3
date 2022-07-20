package com.epam.esm.service;

/**
 * Thrown when one of the fields of object passed with request is invalid.
 */
public class InvalidFieldValueException extends Exception{
    public InvalidFieldValueException(String message) {
        super(message);
    }
}
