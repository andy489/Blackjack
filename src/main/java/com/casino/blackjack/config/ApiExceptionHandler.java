package com.casino.blackjack.config;

import com.casino.blackjack.model.exception.ApiErrorResponse;
import com.casino.blackjack.model.exception.UserNotActiveException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({UserNotActiveException.class})
    protected ResponseEntity<ApiErrorResponse> handleApiException(UserNotActiveException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(
                ex.getStatus(), ex.getMessage(), Instant.now()), ex.getStatus());
    }

}
