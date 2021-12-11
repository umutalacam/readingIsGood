package org.umutalacam.readingapp.customer.exception;

import org.springframework.http.HttpStatus;
import org.umutalacam.readingapp.system.exception.RestException;

public class DuplicateRecordException extends RestException {
    public DuplicateRecordException(String message) {
        super(message, HttpStatus.BAD_REQUEST, null);
    }
}
