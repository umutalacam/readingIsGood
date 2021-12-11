package org.umutalacam.readingapp.book.exception;

import org.springframework.http.HttpStatus;
import org.umutalacam.readingapp.system.exception.RestException;
import org.umutalacam.readingapp.system.exception.RestExceptionResponse;

import java.util.List;

public class BookValidationException extends RestException {
    private final List<String> errorList;

    public BookValidationException(String message, List<String> errorList) {
        super(message, HttpStatus.BAD_REQUEST, null);
        this.errorList = errorList;
    }

    @Override
    public RestExceptionResponse buildResponse() {
        RestExceptionResponse response =  super.buildResponse();
        response.setErrors(errorList);
        return response;
    }
}
