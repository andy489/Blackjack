package com.casino.blackjack.model.exception;

import org.springframework.http.HttpStatus;

public class UserNotActiveException extends RuntimeException {

    private final HttpStatus status;

    public UserNotActiveException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public UserNotActiveException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

}
