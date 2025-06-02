package com.entertainment.port.outgoing.db.repository;

import com.entertainment.domain.movie.core.MovieRating;
import com.entertainment.domain.movie.core.OverallRating;
import com.entertainment.port.outgoing.db.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

public interface RatingRepository extends JpaRepository<RatingEntity, UUID> {

    @Query(value = "SELECT new com.entertainment.domain.movie.core.MovieRating(" +
            "m.id," +
            "m.title," +
            "m.description," +
            "m.producer," +
            "m.movieLanguage," +
            "m.durationInMinutes," +
            "r.userId," +
            "r.ratingValue," +
            "r.comment) " +
            "FROM rating r INNER JOIN movie m ON m.id=r.movieId " +
            "WHERE r.userId=:userId")
    Set<MovieRating> getRatedMovies(UUID userId);

    @Query("SELECT new com.entertainment.domain.movie.core.OverallRating(" +
            "r.movieId," +
            "COUNT(r)," +
            "SUM(r.ratingValue)) " +
            "FROM rating r WHERE r.movieId IN :movieId GROUP BY r.movieId")
    Set<OverallRating> getOverallRating(Set<UUID> movieId);

    @Query("SELECT new com.entertainment.domain.movie.core.OverallRating(" +
            "r.movieId," +
            "COUNT(r)," +
            "SUM(r.ratingValue)) " +
            "FROM rating r WHERE r.movieId=:movieId GROUP BY r.movieId")
    OverallRating getOverallRating(UUID movieId);
}
