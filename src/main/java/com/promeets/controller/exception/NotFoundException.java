package com.promeets.controller.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseControllerException {
    public NotFoundException() {
        this.httpStatus = HttpStatus.NOT_FOUND;
        this.responseErrorMessage = new NotFoundResponseErrorMessage();
    }
}
