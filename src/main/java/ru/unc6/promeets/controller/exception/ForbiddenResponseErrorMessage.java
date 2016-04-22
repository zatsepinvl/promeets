package ru.unc6.promeets.controller.exception;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Properties;

/**
 * Created by Vladimir on 12.04.2016.
 */
public class ForbiddenResponseErrorMessage extends ResponseErrorMessage {
    public static final String FORBIDDEN_ERROR_MESSAGE = "No access to the action";

    @Resource(name = "appProperties")
    private Properties properties;

    public ForbiddenResponseErrorMessage() {
        setMessage(FORBIDDEN_ERROR_MESSAGE);
    }

    @PostConstruct
    public void init() {

    }

}
