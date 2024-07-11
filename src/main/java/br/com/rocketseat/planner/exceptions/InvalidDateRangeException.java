package br.com.rocketseat.planner.exceptions;

public class InvalidDateRangeException extends RuntimeException {
    public InvalidDateRangeException(final String message) {
        super(message);
    }
}
