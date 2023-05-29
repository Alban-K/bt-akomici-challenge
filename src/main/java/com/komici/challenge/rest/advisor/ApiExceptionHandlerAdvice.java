package com.komici.challenge.rest.advisor;

import com.google.common.base.Throwables;
import com.komici.challenge.exception.BTNoEntityFoundException;

import com.komici.challenge.exception.BTOperationNotAllowedException;
import com.komici.challenge.rest.api.ApiResponseError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@ControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandlerAdvice {

    private final Logger log = LoggerFactory.getLogger(ApiExceptionHandlerAdvice.class);

    /**
     * Handle exceptions thrown by handlers.
     */
    @ExceptionHandler(value = BTNoEntityFoundException.class)
    public ResponseEntity<ApiResponseError> exception(BTNoEntityFoundException exception) {


        logError(exception);

        return new ResponseEntity<>(new ApiResponseError(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle exceptions thrown by handlers.
     */
    @ExceptionHandler(value = BTOperationNotAllowedException.class)
    public ResponseEntity<ApiResponseError> exception(BTOperationNotAllowedException exception) {


        logError(exception);

        return new ResponseEntity<>(new ApiResponseError(exception.getMessage()), HttpStatus.CONFLICT);
    }

    /**
     * Handle exceptions thrown by handlers.
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseError> exception(MethodArgumentNotValidException exception, WebRequest request) {


        logError(exception);

        return new ResponseEntity<>(buildErrorBindingValidation(exception), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle exceptions thrown by handlers.
     */
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseError> exception(DataIntegrityViolationException exception) {


        logError(exception);

        return new ResponseEntity<>(new ApiResponseError(exception.getMostSpecificCause().getMessage()), HttpStatus.BAD_REQUEST);
    }

    private void logError(Exception exception) {
        log.error("Exception occurred while a controller of the application was executing  : {} ", exception.getMessage());

        log.error("Stack Track {}", Throwables.getStackTraceAsString(exception));
    }

    private static ApiResponseError buildErrorBindingValidation(MethodArgumentNotValidException exp) {

        List<String> errorBindings = new ArrayList<>();

        exp.getBindingResult().getFieldErrors().stream().filter(Objects::nonNull)
                .forEach(field -> errorBindings.add(field.getField() + " " + field.getDefaultMessage()));

        return new ApiResponseError(errorBindings);
    }

}
