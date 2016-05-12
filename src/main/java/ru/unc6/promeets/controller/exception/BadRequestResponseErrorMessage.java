package ru.unc6.promeets.controller.exception;

/**
 * Created by Vladimir on 12.05.2016.
 */
public class BadRequestResponseErrorMessage extends ResponseErrorMessage {
    private static final String BAD_REQUEST_MESSAGE = "Bad request.";

    public BadRequestResponseErrorMessage() {
        this.message = BAD_REQUEST_MESSAGE;
    }
}
