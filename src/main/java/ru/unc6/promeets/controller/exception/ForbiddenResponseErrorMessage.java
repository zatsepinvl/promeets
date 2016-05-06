package ru.unc6.promeets.controller.exception;

/**
 * Created by Vladimir on 12.04.2016.
 */
public class ForbiddenResponseErrorMessage extends ResponseErrorMessage {
    public static final String FORBIDDEN_ERROR_MESSAGE = "No access to the action";

    public ForbiddenResponseErrorMessage() {
        setMessage(FORBIDDEN_ERROR_MESSAGE);
    }

}
