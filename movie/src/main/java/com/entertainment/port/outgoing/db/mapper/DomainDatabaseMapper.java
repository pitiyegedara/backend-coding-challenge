package com.entertainment.port.outgoing.db.mapper;

import com.entertainment.domain.movie.core.Movie;
import com.entertainment.domain.movie.core.Rating;
import com.entertainment.port.outgoing.db.entity.MovieEntity;
import com.entertainment.port.outgoing.db.entity.RatingEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DomainDatabaseMapper {

    /**
     * Maps the domain movie to a movie entity
     *
     * @param movie domain movie
     * @return MovieEntity
     */
    public MovieEntity mapFromDomainMovie(Movie movie) {
        return MovieEntity.builder()
                .title(movie.getTitle())
                .description(movie.getDescription())
                .producer(movie.getProducer())
                .durationInMinutes(movie.getDurationInMinutes())
                .movieLanguage(movie.getMovieLanguage())
                .build();
    }

    /**
     * Maps the database movie entity to a domain movie object
     *
     * @param movieEntity instance of the movie entity
     * @return movie object
     */
    public Movie mapFromMovieEntity(MovieEntity movieEntity) {
        return Movie.builder().id(movieEntity.getId()).title(movieEntity.getTitle())
                .description(movieEntity.getDescription())
                .durationInMinutes(movieEntity.getDurationInMinutes())
                .movieLanguage(movieEntity.getMovieLanguage())
                .producer(movieEntity.getProducer())
                .createdAt(movieEntity.getCreatedAt())
                .updatedAt(movieEntity.getUpdatedAt())
                .build();
    }

    /**
     * Maps the domain rating details to RatingEntity
     * @param rating domain details of the rating
     * @return RatingEntity
     */
    public RatingEntity mapFromDomainRating(Rating rating) {
        return RatingEntity.builder()
                .userId(rating.getUserId())
                .movieId(rating.getMovieId())
                .ratingValue(rating.getRatingValue())
                .comment(rating.getComment())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

}
