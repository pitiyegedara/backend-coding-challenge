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
        return new MovieEntity()
                .setTitle(movie.getTitle())
                .setDescription(movie.getDescription())
                .setProducer(movie.getProducer())
                .setDurationInMinutes(movie.getDurationInMinutes())
                .setCoverImage(movie.getCoverImage())
                .setMovieLanguage(movie.getMovieLanguage());
    }

}
