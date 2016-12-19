package com.promeets.controller.exception;

/**
 * Created by Vladimir on 06.04.2016.
 */
public class AuthenticationResponseErrorMessage extends ResponseErrorMessage {
    public static final String MESSAGE = "Invalid login and password";

    public AuthenticationResponseErrorMessage() {
        super(MESSAGE);
    }
}
