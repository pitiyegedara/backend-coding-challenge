package com.entertainment.domain.movie.service;

import com.entertainment.domain.movie.core.Movie;
import com.entertainment.domain.movie.outgoing.MovieStorageService;
import com.entertainment.domain.movie.outgoing.RatingStorageService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MovieService {

    private final MovieStorageService movieStorageService;
    private final RatingStorageService ratingStorageService;

    public MovieService(MovieStorageService movieStorageService, RatingStorageService ratingStorageService) {
        this.movieStorageService = movieStorageService;
        this.ratingStorageService = ratingStorageService;
    }

    public void createMovie(Movie movie) {
        movieStorageService.saveMovie(movie);
    }

    public Movie viewMovie(UUID id) {
       var movie = movieStorageService.viewMovie(id);
       var overallRating = ratingStorageService.getOverallRating(id);
       if(overallRating != null) {
           movie.setOverallRating(overallRating.calculateOverallRating());
           movie.setTotalRatings(overallRating.rateCount());
       }
        return movie;
    }
}
