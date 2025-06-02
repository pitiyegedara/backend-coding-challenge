package com.entertainment.port.outgoing.db.service;

import com.entertainment.domain.movie.core.MovieRating;
import com.entertainment.domain.movie.core.OverallRating;
import com.entertainment.domain.movie.core.Rating;
import com.entertainment.domain.movie.exception.RateCreateException;
import com.entertainment.domain.movie.exception.RatingDetailAccessException;
import com.entertainment.domain.movie.outgoing.RatingStorageService;
import com.entertainment.port.outgoing.db.mapper.DomainDatabaseMapper;
import com.entertainment.port.outgoing.db.repository.RatingRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
public class RatingStorageServiceImpl implements RatingStorageService {

    private final DomainDatabaseMapper domainDatabaseMapper;
    private final RatingRepository ratingRepository;

    public RatingStorageServiceImpl(DomainDatabaseMapper domainDatabaseMapper, RatingRepository ratingRepository) {
        this.domainDatabaseMapper = domainDatabaseMapper;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void saveRating(Rating rating) {
        try {
            var ratingEntity = domainDatabaseMapper.mapFromDomainRating(rating);
            ratingRepository.save(ratingEntity);
            rating.setId(ratingEntity.getId());
        } catch (DataAccessException e) {
            throw new RateCreateException("save to database failed");
        }
    }

    @Override
    public Set<MovieRating> getRatedMovies(UUID userId) {
        try {
            return ratingRepository.getRatedMovies(userId);
        } catch (DataAccessException e) {
            throw new RatingDetailAccessException("could not fetch the rating info from database");
        }
    }

    @Override
    public Set<OverallRating> getOverallRatings(Set<UUID> movieIds) {
        try {
            return ratingRepository.getOverallRating(movieIds);
        } catch (DataAccessException e) {
            throw new RatingDetailAccessException("could not fetch the rating info from database");
        }
    }

    @Override
    public OverallRating getOverallRating(UUID movieId) {
        try {
            return ratingRepository.getOverallRating(movieId);
        } catch (DataAccessException e) {
            throw new RatingDetailAccessException("could not fetch the rating info from database");
        }
    }
}
