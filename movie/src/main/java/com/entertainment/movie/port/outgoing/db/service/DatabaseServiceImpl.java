package com.entertainment.movie.port.outgoing.db.service;

import com.entertainment.movie.domain.exception.MovieCreateException;
import com.entertainment.movie.domain.service.DatabaseService;
import com.entertainment.movie.domain.service.core.Movie;
import com.entertainment.movie.port.outgoing.db.mapper.DomainDatabaseMapper;
import com.entertainment.movie.port.outgoing.db.repository.MovieRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    private final DomainDatabaseMapper domainDatabaseMapper;
    private final MovieRepository movieRepository;

    public DatabaseServiceImpl(
            DomainDatabaseMapper domainDatabaseMapper,
            MovieRepository movieRepository
    ) {
        this.domainDatabaseMapper = domainDatabaseMapper;
        this.movieRepository = movieRepository;
    }

    @Override
    public void saveMovie(Movie movie) throws MovieCreateException {
        try {
            var movieEntity = domainDatabaseMapper.mapFromDomainMovie(movie);
            movieRepository.save(movieEntity);
            movie.setId(movieEntity.getId());
        } catch (DataAccessException e) {
            throw new MovieCreateException(e.getMessage());
        }
    }


}
