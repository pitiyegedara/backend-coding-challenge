package com.entertainment.domain.movie.service;

import com.entertainment.domain.movie.core.Movie;
import com.entertainment.domain.movie.outgoing.MovieStorageService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MovieService {

    private final MovieStorageService movieStorageService;

    public MovieService(MovieStorageService movieStorageService) {
        this.movieStorageService = movieStorageService;
    }

    public void createMovie(Movie movie) {
        movieStorageService.saveMovie(movie);
    }

    public Movie viewMovie(UUID id) {
        return movieStorageService.viewMovie(id);
    }
}
