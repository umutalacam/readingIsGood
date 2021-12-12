package org.umutalacam.readingapp.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.Instant;

public class RestException extends Exception {
    private HttpStatus status;
    private Timestamp timestamp;
    private String path;

    public RestException() {
        this.timestamp = Timestamp.from(Instant.now());
    }

    public RestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public RestException(String message, HttpStatus status, String path) {
        super(message);
        this.status = status;
        this.timestamp = Timestamp.from(Instant.now());
        this.path = path;
    }

    public RestExceptionResponse buildResponse() {
        RestExceptionResponse restExceptionResponse = new RestExceptionResponse();
        restExceptionResponse.setStatus(status.value());
        restExceptionResponse.setMessage(this.getMessage());
        restExceptionResponse.setTimestamp(this.timestamp);
        restExceptionResponse.setPath(this.path);

        return restExceptionResponse;
    }

    public ResponseEntity<RestExceptionResponse> getResponseEntity() {
        return ResponseEntity.status(this.status).body(this.buildResponse());
    }
}
