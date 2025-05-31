package com.entertainment.movie.port.outgoing.db.mapper;

import com.entertainment.movie.domain.service.core.Movie;
import com.entertainment.movie.port.outgoing.db.entity.MovieEntity;
import org.springframework.stereotype.Component;

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

}
