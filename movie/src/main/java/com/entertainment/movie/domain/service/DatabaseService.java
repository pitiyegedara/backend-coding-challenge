package com.entertainment.movie.domain.service;

import com.entertainment.movie.domain.service.core.Movie;

import java.util.UUID;

public interface DatabaseService {

    void saveMovie(Movie movie);

    Movie viewMovie(UUID id);

}
