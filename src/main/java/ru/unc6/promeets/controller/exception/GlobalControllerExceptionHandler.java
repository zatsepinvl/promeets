package ru.unc6.promeets.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Vladimir on 06.04.2016.
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseError handleException(Exception ex) {
        return new ResponseError(ex.getMessage());
    }


    @ExceptionHandler(value = BaseControllerException.class)
    @ResponseBody
    public ResponseEntity<ResponseError> handleControllerException(BadRequestException ex) {
        return new ResponseEntity<>(ex.getResponseError(), ex.getHttpStatus());
    }


}
