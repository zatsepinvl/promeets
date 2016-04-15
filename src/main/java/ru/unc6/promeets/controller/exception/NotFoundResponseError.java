package ru.unc6.promeets.controller.exception;

/**
 * Created by Vladimir on 12.04.2016.
 */
public class NotFoundResponseError extends ResponseError {
    public static final String NOT_FOUND_ERROR_MESSAGE = "Entity not found";

    public NotFoundResponseError() {
        setMessage(NOT_FOUND_ERROR_MESSAGE);
    }
}
