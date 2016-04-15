package ru.unc6.promeets.controller.exception;

/**
 * Created by Vladimir on 12.04.2016.
 */
public class ForbiddenResponseError extends ResponseError {
    public static final String FORBIDDEN_ERROR_MESSAGE = "No access to the action";

    public ForbiddenResponseError() {
        setMessage(FORBIDDEN_ERROR_MESSAGE);
    }
}
