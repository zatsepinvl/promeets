package ru.unc6.promeets.controller.exception;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Vladimir on 12.04.2016.
 */
public class NotFoundResponseErrorMessage extends ResponseErrorMessage {

    @Value("${rest-message-404}")
    private String notFoundErrorMessage;

    public NotFoundResponseErrorMessage() {
        setMessage(notFoundErrorMessage);
    }
}
