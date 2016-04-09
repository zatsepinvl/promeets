package ru.unc6.promeets.controller.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by Vladimir on 06.04.2016.
 */
public class BadRequestException extends BaseControllerException {
    public BadRequestException() {
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
