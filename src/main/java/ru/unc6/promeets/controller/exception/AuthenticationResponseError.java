package ru.unc6.promeets.controller.exception;

/**
 * Created by Vladimir on 06.04.2016.
 */
public class AuthenticationResponseError extends ResponseError {
    public static final String MESSAGE = "Invalid login and password";

    public AuthenticationResponseError() {
        super(MESSAGE);
    }
}
