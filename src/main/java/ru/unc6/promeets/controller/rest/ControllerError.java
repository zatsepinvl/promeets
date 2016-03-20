package ru.unc6.promeets.controller.rest;

/**
 * Created by Vladimir on 19.03.2016.
 */
public class ControllerError {
    public static final String BASE_ERROR_MESSAGE = "Something went wrong. Please,try again later.";
    private String message;

    public ControllerError() {
        message = BASE_ERROR_MESSAGE;
    }

    public ControllerError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
