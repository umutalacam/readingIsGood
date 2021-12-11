package org.umutalacam.readingapp.order.exception;

import org.springframework.http.HttpStatus;
import org.umutalacam.readingapp.system.exception.RestException;
import org.umutalacam.readingapp.system.exception.RestExceptionResponse;

import java.util.ArrayList;
import java.util.List;

public class InvalidOrderException extends RestException {
    private List<String> errorList;

    public InvalidOrderException() {
        super("Invalid order.", HttpStatus.BAD_REQUEST, null);
        errorList = new ArrayList<>();
    }

    public void addErrorMessage(String error) {
        errorList.add(error);
    }

    @Override
    public RestExceptionResponse buildResponse() {
        RestExceptionResponse response =  super.buildResponse();
        response.setErrors(this.errorList);
        return response;
    }
}
