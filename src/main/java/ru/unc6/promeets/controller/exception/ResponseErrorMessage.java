package ru.unc6.promeets.controller.exception;

import ru.unc6.promeets.controller.ResponseMessage;

public class ResponseErrorMessage extends ResponseMessage {

    public static final String BASE_ERROR_MESSAGE = "Something went wrong. Please,try again later.";

    public ResponseErrorMessage() {
        message = BASE_ERROR_MESSAGE;
    }

    public ResponseErrorMessage(String message) {
        this.message = message;
    }

}
