package org.umutalacam.readingapp.customer.exception;

import org.springframework.http.HttpStatus;
import org.umutalacam.readingapp.system.exception.RestException;
import org.umutalacam.readingapp.system.exception.RestExceptionResponse;

import java.util.List;

public class CustomerValidationException extends RestException {
    List<String> errors;

    public CustomerValidationException(List<String> errors) {
        super("Customer is not valid.", HttpStatus.BAD_REQUEST, null);
        this.errors = errors;
    }

    @Override
    public RestExceptionResponse buildResponse() {
        RestExceptionResponse response = super.buildResponse();
        response.setErrors(this.errors);
        return response;
    }
}
