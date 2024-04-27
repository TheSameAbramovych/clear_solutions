package com.bohdan.abramovych.clear_solutions.core.exception;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.util.Objects;

public class ApiException extends RuntimeException {

    public ApiException(String messageTemplate, String... params) {
        super(MessageFormatter.arrayFormat(messageTemplate, params).getMessage());
    }
}
