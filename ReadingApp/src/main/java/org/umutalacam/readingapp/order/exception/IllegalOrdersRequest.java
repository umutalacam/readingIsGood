package org.umutalacam.readingapp.order.exception;

import org.springframework.http.HttpStatus;
import org.umutalacam.readingapp.system.exception.RestException;

public class IllegalOrdersRequest extends RestException {
    public IllegalOrdersRequest(String message) {
        super(message, HttpStatus.BAD_REQUEST, null);
    }
}
