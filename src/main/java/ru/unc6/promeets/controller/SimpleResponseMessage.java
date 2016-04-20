package ru.unc6.promeets.controller;

/**
 * Created by Vladimir on 21.04.2016.
 */
public class SimpleResponseMessage {
    private String message;

    public String getMessage() {
        return message;
    }

    public SimpleResponseMessage setMessage(String message) {
        this.message = message;
        return this;
    }
}
