package com.epam.esm.exceptionhandling;

import com.epam.esm.exception.InvalidSortTypeException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.InvalidFieldValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Provides exception handling across @RequestMapping methods.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles ResourceNotFoundException.
     *
     * @param ex
     * @return ResponseEntity with proper custom error message for ResourceNotFoundException.
     * @see ResourceNotFoundException
     */
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponseBody> handleResourceNotFoundException(final ResourceNotFoundException ex) {
        ErrorResponseBody erb = new ErrorResponseBody(ex.getMessage(), ErrorCodes.ID_NOT_FOUND.toString());
        return new ResponseEntity<>(erb, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles InvalidFieldValueException.
     *
     * @param ex
     * @return ResponseEntity with proper custom error message for InvalidFieldValueException.
     * @see InvalidFieldValueException
     */
    @ExceptionHandler({InvalidFieldValueException.class})
    public ResponseEntity<ErrorResponseBody> handleInvalidFieldValueException(final InvalidFieldValueException ex) {
        ErrorResponseBody erb = new ErrorResponseBody(ex.getMessage(), ErrorCodes.INVALID_FIELD.toString());
        return new ResponseEntity<>(erb, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles InvalidSortTypeException.
     *
     * @param ex
     * @return ResponseEntity with proper custom error message for InvalidSortTypeException.
     * @see InvalidSortTypeException
     */
    @ExceptionHandler({InvalidSortTypeException.class})
    public ResponseEntity<ErrorResponseBody> handleInvalidFieldValueException(final InvalidSortTypeException ex) {
        ErrorResponseBody erb = new ErrorResponseBody(ex.getMessage(), ErrorCodes.INVALID_SORT_VALUE.toString());
        return new ResponseEntity<>(erb, HttpStatus.BAD_REQUEST);
    }
}
