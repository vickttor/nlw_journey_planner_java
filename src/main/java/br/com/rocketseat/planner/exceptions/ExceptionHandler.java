package br.com.rocketseat.planner.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDateRange(InvalidDateRangeException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Invalid Date", ex.getMessage(), 400));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEmail(InvalidEmailException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Invalid Email", ex.getMessage(), 400));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MissingPropertyException.class)
    public ResponseEntity<ErrorResponse> handleMissingProperty(MissingPropertyException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Missing Property", ex.getMessage(), 400));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MaxLengthExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxLengthExceeded(MaxLengthExceededException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Max Length Exceeded", ex.getMessage(), 400));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> handleDateTimeParsing(DateTimeParseException  ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Date Time Parsing", ex.getMessage(), 400));
    }
}
