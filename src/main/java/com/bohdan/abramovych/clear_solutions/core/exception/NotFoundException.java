package com.bohdan.abramovych.clear_solutions.core.exception;

import org.slf4j.helpers.MessageFormatter;

import java.util.Objects;

public class NotFoundException extends ApiException {

    public NotFoundException(String messageTemplate, String... params) {
        super(messageTemplate, params);
    }
}
