package ru.unc6.promeets.controller;

/**
 * Created by Vladimir on 21.04.2016.
 */
public class ResponseMessage {
    protected String message;

    public String getMessage() {
        return message;
    }

    public ResponseMessage setMessage(String message) {
        this.message = message;
        return this;
    }
}
