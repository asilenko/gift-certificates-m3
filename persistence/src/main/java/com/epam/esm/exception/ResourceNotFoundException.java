package com.epam.esm.exception;

/**
 * Thrown when no resource with the given id was found.
 */
public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
