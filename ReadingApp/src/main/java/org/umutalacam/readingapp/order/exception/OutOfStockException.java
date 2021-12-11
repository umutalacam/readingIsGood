package org.umutalacam.readingapp.order.exception;

import org.springframework.http.HttpStatus;
import org.umutalacam.readingapp.system.exception.RestException;

public class OutOfStockException extends RestException {
    public OutOfStockException() {
        super("The ordered items are out of stock", HttpStatus.EXPECTATION_FAILED, null);
    }
}
