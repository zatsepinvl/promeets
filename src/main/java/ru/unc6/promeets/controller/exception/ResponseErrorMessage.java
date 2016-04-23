package ru.unc6.promeets.controller.exception;

import ru.unc6.promeets.controller.ResponseMessage;

/**
 * Created by Vladimir on 19.03.2016.
 */
public class ResponseErrorMessage extends ResponseMessage {
    public static final String BASE_ERROR_MESSAGE = "Something went wrong. Please,try again later.";

    public ResponseErrorMessage() {
        message = BASE_ERROR_MESSAGE;
    }

    public ResponseErrorMessage(String message) {
        this.message = message;
    }

}
