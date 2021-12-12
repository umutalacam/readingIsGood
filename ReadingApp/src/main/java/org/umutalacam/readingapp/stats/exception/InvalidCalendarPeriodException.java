package org.umutalacam.readingapp.stats.exception;

import org.springframework.http.HttpStatus;
import org.umutalacam.readingapp.system.exception.RestException;

public class InvalidCalendarPeriodException extends RestException {
    public InvalidCalendarPeriodException() {
        super("Calendar period is cannot be larger than current month", HttpStatus.BAD_REQUEST, null);
    }
}
