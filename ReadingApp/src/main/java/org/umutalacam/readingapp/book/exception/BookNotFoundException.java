package org.umutalacam.readingapp.book.exception;

import org.springframework.http.HttpStatus;
import org.umutalacam.readingapp.system.exception.RestException;
import org.umutalacam.readingapp.system.exception.RestExceptionResponse;

import java.util.List;

public class BookNotFoundException extends RestException {

    public BookNotFoundException(String message, String path) {
        super(message, HttpStatus.NOT_FOUND, path);
    }

    @Override
    public RestExceptionResponse buildResponse() {
        RestExceptionResponse response =  super.buildResponse();
        response.setErrors(List.of("Book name is not valid.", "Book id is not valid."));
        return response;
    }
}
