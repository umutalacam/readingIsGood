package org.umutalacam.readingapp.customer.exception;

import org.springframework.http.HttpStatus;
import org.umutalacam.readingapp.system.exception.RestException;

public class CustomerNotFoundException extends RestException {

    public CustomerNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, null);
    }
}
