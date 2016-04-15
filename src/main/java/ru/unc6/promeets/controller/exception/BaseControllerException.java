package ru.unc6.promeets.controller.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by Vladimir on 06.04.2016.
 */
public class BaseControllerException extends RuntimeException {

    protected ResponseError responseError;
    protected HttpStatus httpStatus;

    public ResponseError getResponseError() {
        return responseError;
    }

    public BaseControllerException setResponseError(ResponseError responseError) {
        this.responseError = responseError;
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
