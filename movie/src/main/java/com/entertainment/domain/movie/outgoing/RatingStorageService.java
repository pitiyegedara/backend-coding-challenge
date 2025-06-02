package com.entertainment.domain.movie.outgoing;

import com.entertainment.domain.movie.core.MovieRating;
import com.entertainment.domain.movie.core.OverallRating;
import com.entertainment.domain.movie.core.Rating;

import java.util.Set;
import java.util.UUID;

public interface RatingStorageService {

    void saveRating(Rating rating);

    Set<MovieRating> getRatedMovies(UUID userId);

    Set<OverallRating> getOverallRatings(Set<UUID> movieIds);

    OverallRating getOverallRating(UUID movieId);

}
