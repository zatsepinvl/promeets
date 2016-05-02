package ru.unc6.promeets.controller.exception;

import org.springframework.http.HttpStatus;


public class BaseControllerException extends RuntimeException {

    protected ResponseErrorMessage responseErrorMessage;
    protected HttpStatus httpStatus;

    public ResponseErrorMessage getResponseErrorMessage() {
        return responseErrorMessage;
    }

    public BaseControllerException setResponseErrorMessage(ResponseErrorMessage responseErrorMessage) {
        this.responseErrorMessage = responseErrorMessage;
        return this;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public BaseControllerException setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }
}
