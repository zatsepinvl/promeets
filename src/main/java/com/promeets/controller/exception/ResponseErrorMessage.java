package com.promeets.controller.exception;

import com.promeets.controller.ResponseMessage;

public class ResponseErrorMessage extends ResponseMessage {

    public static final String BASE_ERROR_MESSAGE = "Something went wrong. Please,try again later.";

    public ResponseErrorMessage() {
        message = BASE_ERROR_MESSAGE;
    }

    public ResponseErrorMessage(String message) {
        this.message = message;
    }

}
