package com.promeets.controller.exception;

/**
 * Created by Vladimir on 19.03.2016.
 */
public class ResponseError {
    public static final String BASE_ERROR_MESSAGE = "Something went wrong. Please,try again later.";
    private String message;

    public ResponseError() {
        message = BASE_ERROR_MESSAGE;
    }

    public ResponseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
