package com.entertainment.domain.movie.exception;

public class RatingDetailAccessException extends RuntimeException{

    public RatingDetailAccessException(String message) {
        super("An error occurred while accessing the rating data: " + message);
    }
}
