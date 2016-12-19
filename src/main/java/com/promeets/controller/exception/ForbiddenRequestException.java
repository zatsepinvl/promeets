package com.promeets.controller.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by Vladimir on 12.04.2016.
 */
public class ForbiddenRequestException extends BaseControllerException {
    public ForbiddenRequestException() {
        this.httpStatus = HttpStatus.FORBIDDEN;
        this.responseErrorMessage = new ForbiddenResponseErrorMessage();
    }
}
