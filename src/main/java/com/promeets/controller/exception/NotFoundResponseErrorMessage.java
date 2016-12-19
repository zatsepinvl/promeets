package com.promeets.controller.exception;

public class NotFoundResponseErrorMessage extends ResponseErrorMessage {

    public static final String NOT_FOUND_ERROR_MESSAGE = "Entity not found";

    public NotFoundResponseErrorMessage() {
        this.message = NOT_FOUND_ERROR_MESSAGE;
    }
}
