package org.umutalacam.readingapp.system.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestExceptionResponse> handleRestExceptions(RestException exception) {
        return exception.getResponseEntity();
    }
}
