package com.komici.challenge.rest.advisor;

import com.google.common.base.Throwables;
import com.komici.challenge.exception.BTNoEntityFoundException;

import com.komici.challenge.rest.api.ApiResponseError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandlerAdvice {

    private Logger log = LoggerFactory.getLogger(ApiExceptionHandlerAdvice.class);

    /**
     * Handle exceptions thrown by handlers.
     */
    @ExceptionHandler(value = BTNoEntityFoundException.class)
    public ResponseEntity<ApiResponseError> exception(BTNoEntityFoundException exception, WebRequest request) {


        logError(exception);

        return new ResponseEntity<>(new ApiResponseError(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    private void logError(Exception exception) {
        log.error("Exception occurred while a controller of Energy Storage application was executing  : {} ", exception.getMessage());

        log.error("Stack Track {}", Throwables.getStackTraceAsString(exception));
    }

}
