package ru.unc6.promeets.controller.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by Vladimir on 06.04.2016.
 */
public class NotFoundException extends BaseControllerException {
    public NotFoundException() {
        this.httpStatus = HttpStatus.NOT_FOUND;
        this.responseError = new NotFoundResponseError();
    }
}
