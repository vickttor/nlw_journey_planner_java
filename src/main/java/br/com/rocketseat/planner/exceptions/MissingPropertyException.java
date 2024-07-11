package br.com.rocketseat.planner.exceptions;

public class MissingPropertyException extends RuntimeException {
    public MissingPropertyException(final String message) {
        super("Missing required property: " + message);
    }
}
