package ru.unc6.promeets.controller.exception;

/**
 * Created by Vladimir on 12.04.2016.
 */
public class NotFoundResponseErrorMessage extends ResponseErrorMessage {
    public static final String NOT_FOUND_ERROR_MESSAGE = "Entity not found";

    public NotFoundResponseErrorMessage() {
        setMessage(NOT_FOUND_ERROR_MESSAGE);
    }
}
