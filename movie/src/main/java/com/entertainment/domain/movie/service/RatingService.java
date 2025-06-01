package com.entertainment.domain.movie.service;

import com.entertainment.domain.movie.outgoing.RatingStorageService;
import com.entertainment.domain.movie.core.Rating;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RatingStorageService ratingStorageService;

    public RatingService(RatingStorageService ratingStorageService) {
        this.ratingStorageService = ratingStorageService;
    }

    /**
     * Save rating details
     * @param rating details
     */
    public void createRating(Rating rating) {
        ratingStorageService.saveRating(rating);
    }
}
