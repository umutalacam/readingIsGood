package org.umutalacam.readingapp.security.exception;

import org.springframework.http.HttpStatus;
import org.umutalacam.readingapp.system.exception.RestException;

public class InvalidJwtException extends RestException {
    public InvalidJwtException() {
        super("Invalid jwt token.", HttpStatus.UNAUTHORIZED);
    }
}
