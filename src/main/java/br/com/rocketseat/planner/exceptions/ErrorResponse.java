package br.com.rocketseat.planner.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String error;
    private Integer status;
    private String message;

    public ErrorResponse(String error, String message, Integer status) {
        this.error = error;
        this.message = message;
        this.status = status;
    }

}