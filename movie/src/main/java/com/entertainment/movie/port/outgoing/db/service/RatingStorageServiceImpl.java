package com.entertainment.movie.port.outgoing.db.service;

import com.entertainment.movie.domain.exception.MovieCreateException;
import com.entertainment.movie.domain.exception.RateCreateException;
import com.entertainment.movie.domain.outgoing.RatingStorageService;
import com.entertainment.movie.domain.service.core.Rating;
import com.entertainment.movie.port.outgoing.db.mapper.DomainDatabaseMapper;
import com.entertainment.movie.port.outgoing.db.repository.RatingRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
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
}
