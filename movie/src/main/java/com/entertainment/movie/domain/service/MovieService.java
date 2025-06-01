package com.entertainment.movie.domain.service;

import com.entertainment.movie.domain.service.core.Movie;
import com.entertainment.movie.domain.outgoing.MovieStorageService;
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
