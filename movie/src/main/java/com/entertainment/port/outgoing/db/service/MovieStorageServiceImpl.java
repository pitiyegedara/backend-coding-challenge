package com.entertainment.port.outgoing.db.service;

import com.entertainment.domain.movie.core.Movie;
import com.entertainment.domain.movie.exception.MovieCreateException;
import com.entertainment.domain.movie.exception.MovieNotFoundException;
import com.entertainment.domain.movie.outgoing.MovieStorageService;
import com.entertainment.port.outgoing.db.entity.MovieEntity;
import com.entertainment.port.outgoing.db.mapper.DomainDatabaseMapper;
import com.entertainment.port.outgoing.db.repository.MovieRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MovieStorageServiceImpl implements MovieStorageService {

    private final DomainDatabaseMapper domainDatabaseMapper;
    private final MovieRepository movieRepository;

    public MovieStorageServiceImpl(
            DomainDatabaseMapper domainDatabaseMapper,
            MovieRepository movieRepository
    ) {
        this.domainDatabaseMapper = domainDatabaseMapper;
        this.movieRepository = movieRepository;
    }

    /**
     * Save the movie data in the database
     *
     * @param movie domain movie object
     * @throws MovieCreateException if the saving to database failed
     */
    @Override
    public void saveMovie(Movie movie) throws MovieCreateException {
        try {
            var movieEntity = domainDatabaseMapper.mapFromDomainMovie(movie);
            movieRepository.save(movieEntity);
            movie.setId(movieEntity.getId());
        } catch (DataAccessException e) {
            throw new MovieCreateException("save to database failed");
        }
    }

    /**
     * View the movie details of the given id
     *
     * @param id id of the movie
     * @return Domain movie object
     */
    @Override
    public Movie viewMovie(UUID id) {
        MovieEntity movieEntity = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id.toString()));
        return domainDatabaseMapper.mapFromMovieEntity(movieEntity);
    }


}
