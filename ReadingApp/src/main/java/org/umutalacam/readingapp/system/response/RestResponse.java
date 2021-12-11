package org.umutalacam.readingapp.system.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RestResponse {
    private Timestamp timestamp;
    private String message;

    public RestResponse() {
        this.timestamp = Timestamp.from(Instant.now());
    }

    public RestResponse(String message) {
        this.message = message;
        this.timestamp = Timestamp.from(Instant.now());
    }
}
