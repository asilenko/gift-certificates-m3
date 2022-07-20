package com.epam.esm.exceptionhandling;

/**
 * Response body object for error messages with custom codes.
 */
public class ErrorResponseBody {
    private final String errorMessage;
    private final String errorCode;

    public ErrorResponseBody(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
