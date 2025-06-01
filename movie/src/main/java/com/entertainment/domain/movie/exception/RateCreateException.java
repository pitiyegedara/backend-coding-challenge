package com.entertainment.domain.movie.exception;

public class RateCreateException extends RuntimeException {

    public RateCreateException(String message) {
        super("An error occurred while trying save the rating: " + message);
    }
}
