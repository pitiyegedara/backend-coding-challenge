package com.entertainment.domain.movie.exception;

public class MovieNotFoundException extends RuntimeException{

    public MovieNotFoundException(String uuid) {
        super("Could not find movie " + uuid + ".");
    }
}
