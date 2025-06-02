package com.entertainment.domain.movie.service;

import com.entertainment.domain.movie.core.MovieRating;
import com.entertainment.domain.movie.core.Rating;
import com.entertainment.domain.movie.outgoing.RatingStorageService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class RatingService {

    private final RatingStorageService ratingStorageService;

    public RatingService(RatingStorageService ratingStorageService) {
        this.ratingStorageService = ratingStorageService;
    }

    /**
     * Save rating details
     *
     * @param rating details
     */
    public void createRating(Rating rating) {
        ratingStorageService.saveRating(rating);
    }

    public Set<MovieRating> getRatedMovies(UUID userId) {
        var ratedMovies = ratingStorageService.getRatedMovies(userId);
        Set<UUID> movieIds = new HashSet<>();
        HashMap<UUID, MovieRating> ratedMovieMap = new HashMap<>();
        ratedMovies.forEach(movieRating -> {
            movieIds.add(movieRating.getMovieId());
            ratedMovieMap.put(movieRating.getMovieId(), movieRating);
        });

        var overallRatingData = ratingStorageService.getOverallRatings(movieIds);
        overallRatingData.forEach(overallRating -> ratedMovieMap.get(overallRating.movieId()).setOverallRating(
                overallRating.calculateOverallRating()).setTotalRatings(overallRating.rateCount()));

        return ratedMovies;
    }
}
