package br.com.rocketseat.planner.exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(final String message) {
        super(message);
    }
}
