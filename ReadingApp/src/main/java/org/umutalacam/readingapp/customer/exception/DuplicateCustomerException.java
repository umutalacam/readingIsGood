package org.umutalacam.readingapp.customer.exception;

import org.springframework.http.HttpStatus;
import org.umutalacam.readingapp.system.exception.RestException;

public class DuplicateCustomerException extends RestException {
    public DuplicateCustomerException(String message) {
        super(message, HttpStatus.BAD_REQUEST, null);
    }
}
