package com.entertainment.movie.domain.outgoing;

import com.entertainment.movie.domain.service.core.Rating;

public interface RatingStorageService {

    void saveRating(Rating rating);
}
