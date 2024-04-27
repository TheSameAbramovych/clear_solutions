package com.bohdan.abramovych.clear_solutions.core.exception;

public class AgeCheckerException extends RuntimeException {
    public AgeCheckerException() {
        super("Age less 18!");
    }
}
