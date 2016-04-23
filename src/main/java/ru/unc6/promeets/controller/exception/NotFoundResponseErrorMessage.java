package ru.unc6.promeets.controller.exception;

import org.springframework.beans.factory.annotation.Value;
import ru.unc6.promeets.controller.ResponseMessage;

/**
 * Created by Vladimir on 12.04.2016.
 */
public class NotFoundResponseErrorMessage extends ResponseErrorMessage {

    @Value("${rest-message-404}")
    private String notFoundErrorMessage;

    @Override
    public String getMessage() {
        return notFoundErrorMessage;
    }

    @Override
    public ResponseMessage setMessage(String message) {
        notFoundErrorMessage = message;
        return this;
    }
}
