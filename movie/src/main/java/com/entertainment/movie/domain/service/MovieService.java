package com.entertainment.movie.domain.service;

import com.entertainment.movie.domain.service.core.Movie;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final DatabaseService databaseService;

    public MovieService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void createMovie(Movie movie) {
        databaseService.saveMovie(movie);
    }
}
