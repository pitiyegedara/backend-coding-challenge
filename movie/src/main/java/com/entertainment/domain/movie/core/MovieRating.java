package com.entertainment.domain.movie.core;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class MovieRating {

    private final UUID movieId;
    private final String movieTitle;
    private final String movieDescription;
    private final String movieProducer;
    private final String movieLanguage;
    private final int movieDuration;

    private final UUID userId;
    private final int userRating;
    private final String comment;

    private BigDecimal overallRating;
    private Long totalRatings;

    public MovieRating(
            UUID movieId,
            String movieTitle,
            String movieDescription,
            String movieProducer,
            String movieLanguage,
            int movieDuration,
            UUID userId,
            int userRating,
            String comment
    ) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieDescription = movieDescription;
        this.movieProducer = movieProducer;
        this.movieLanguage = movieLanguage;
        this.movieDuration = movieDuration;
        this.userId = userId;
        this.userRating = userRating;
        this.comment = comment;
    }

    public MovieRating setOverallRating(BigDecimal overallRating) {
        this.overallRating = overallRating;
        return this;
    }

    public MovieRating setTotalRatings(Long totalRatings) {
        this.totalRatings = totalRatings;
        return this;
    }
}
