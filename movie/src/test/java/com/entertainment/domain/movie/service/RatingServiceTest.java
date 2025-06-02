package com.entertainment.domain.movie.service;

import com.entertainment.domain.movie.core.MovieRating;
import com.entertainment.domain.movie.core.OverallRating;
import com.entertainment.domain.movie.outgoing.RatingStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @InjectMocks
    RatingService ratingService;
    @Mock
    RatingStorageService ratingStorageService;

    @Test
    @DisplayName("service should calculate the overall rating correctly")
    void serviceShouldCalculateOverallRatingCorrectly() {
        var userId = UUID.randomUUID();

        var userRatedMovieOne = new MovieRating(
                UUID.randomUUID(),
                "new movie",
                "new movie description",
                "producer",
                "english",
                30,
                userId,
                5,
                "nice movie"
        );

        var userRatedMovieTwo = new MovieRating(
                UUID.randomUUID(),
                "new movie",
                "new movie description",
                "producer",
                "english",
                30,
                userId,
                5,
                "nice movie"
        );
        Set<MovieRating> movieRatings = new HashSet<>();
        movieRatings.add(userRatedMovieOne);
        movieRatings.add(userRatedMovieTwo);

        Set<UUID> movieIds = new HashSet<>();
        movieIds.add(userRatedMovieOne.getMovieId());
        movieIds.add(userRatedMovieTwo.getMovieId());

        Set<OverallRating> overallRatings = new HashSet<>();
        overallRatings.add(new OverallRating(userRatedMovieOne.getMovieId(), 5L, 34L));
        overallRatings.add(new OverallRating(userRatedMovieTwo.getMovieId(), 7L, 43L));

        when(ratingStorageService.getRatedMovies(userId)).thenReturn(movieRatings);
        when(ratingStorageService.getOverallRatings(movieIds)).thenReturn(overallRatings);

        var movieRatingList = ratingService.getRatedMovies(userId);
        Assertions.assertEquals(2, movieRatingList.size());
        Assertions.assertEquals(5, userRatedMovieOne.getTotalRatings());
        Assertions.assertEquals(BigDecimal.valueOf(6.8), userRatedMovieOne.getOverallRating());
        Assertions.assertEquals(7, userRatedMovieTwo.getTotalRatings());
        Assertions.assertEquals(BigDecimal.valueOf(6.1), userRatedMovieTwo.getOverallRating());
    }

    @Test
    @DisplayName("service should return an empty list when the user has not given any ratings")
    void testGetRatedMovies() {
        var userId = UUID.randomUUID();

        Set<MovieRating> movieRatings = new HashSet<>();

        when(ratingStorageService.getRatedMovies(userId)).thenReturn(movieRatings);

        var movieRatingList = ratingService.getRatedMovies(userId);
        Assertions.assertEquals(0, movieRatingList.size());
    }

}
