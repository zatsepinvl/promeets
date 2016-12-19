package com.promeets.controller.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Vladimir on 06.04.2016.
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger log = Logger.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseErrorMessage handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseErrorMessage(ex.getClass() + " : " + ex.getMessage());
    }


    @ExceptionHandler(value = BaseControllerException.class)
    @ResponseBody
    public ResponseEntity<ResponseErrorMessage> handleControllerException(BaseControllerException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getResponseErrorMessage(), ex.getHttpStatus());
    }


}
