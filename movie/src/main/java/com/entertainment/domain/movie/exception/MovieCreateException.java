package com.entertainment.domain.movie.exception;

public class MovieCreateException extends RuntimeException{

    public MovieCreateException(String message) {
        super("An error occurred while trying create the movie: " + message);
    }
}
