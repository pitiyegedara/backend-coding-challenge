package com.entertainment.domain.movie.outgoing;

import com.entertainment.domain.movie.core.Rating;

public interface RatingStorageService {

    void saveRating(Rating rating);
}
