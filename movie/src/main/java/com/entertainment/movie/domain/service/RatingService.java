package com.entertainment.movie.domain.service;

import com.entertainment.movie.domain.outgoing.RatingStorageService;
import com.entertainment.movie.domain.service.core.Rating;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private RatingStorageService ratingStorageService;

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
