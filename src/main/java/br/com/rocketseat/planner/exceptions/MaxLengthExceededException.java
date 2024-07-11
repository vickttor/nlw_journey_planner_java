package br.com.rocketseat.planner.exceptions;

public class MaxLengthExceededException extends RuntimeException {
    public MaxLengthExceededException(final String propertyName, int maxLength) {
        super("Property '" + propertyName + "' exceeded maximum length of " + maxLength);
    }
}
