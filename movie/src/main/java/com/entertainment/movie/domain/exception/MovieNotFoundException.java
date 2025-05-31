package com.entertainment.movie.domain.exception;

public class MovieNotFoundException extends RuntimeException{

    public MovieNotFoundException(String uuid) {
        super("Could not find movie " + uuid + ".");
    }
}
