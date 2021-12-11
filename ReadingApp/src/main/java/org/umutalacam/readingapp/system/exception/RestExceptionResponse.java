package org.umutalacam.readingapp.system.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RestExceptionResponse {
    private int status;
    private String message;
    private List<String> errors;
    private String path;
    private Timestamp timestamp;
}
