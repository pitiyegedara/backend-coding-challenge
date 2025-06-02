package com.entertainment.port.outgoing.db.mapper;

import com.entertainment.domain.movie.core.Movie;
import com.entertainment.domain.movie.core.Rating;
import com.entertainment.domain.user.core.User;
import com.entertainment.port.outgoing.db.entity.MovieEntity;
import com.entertainment.port.outgoing.db.entity.RatingEntity;
import com.entertainment.port.outgoing.db.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DomainDatabaseMapper {

    /**
     * Maps the domain movie to a movie entity
     *
     * @param movie domain movie
     * @return MovieEntity
     */
    public MovieEntity mapFromDomainMovie(Movie movie) {
        return MovieEntity.builder()
                .title(movie.getTitle())
                .description(movie.getDescription())
                .producer(movie.getProducer())
                .durationInMinutes(movie.getDurationInMinutes())
                .movieLanguage(movie.getMovieLanguage())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    /**
     * Maps the database movie entity to a domain movie object
     *
     * @param movieEntity instance of the movie entity
     * @return movie object
     */
    public Movie mapFromMovieEntity(MovieEntity movieEntity) {
        return Movie.builder().id(movieEntity.getId()).title(movieEntity.getTitle())
                .description(movieEntity.getDescription())
                .durationInMinutes(movieEntity.getDurationInMinutes())
                .movieLanguage(movieEntity.getMovieLanguage())
                .producer(movieEntity.getProducer())
                .createdAt(movieEntity.getCreatedAt())
                .updatedAt(movieEntity.getUpdatedAt())
                .build();
    }

    /**
     * Maps the domain rating details to RatingEntity
     *
     * @param rating domain details of the rating
     * @return RatingEntity
     */
    public RatingEntity mapFromDomainRating(Rating rating) {
        return RatingEntity.builder()
                .userId(rating.getUserId())
                .movieId(rating.getMovieId())
                .ratingValue(rating.getRatingValue())
                .comment(rating.getComment())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    /**
     * Maps the domain user details to the UserEntity
     *
     * @param user domain user details
     * @return UserEntity
     */
    public UserEntity mapFromDomainUser(User user) {
        return UserEntity.builder()
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .address(user.getAddress())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    /**
     * Maps the userEntity to user domain
     *
     * @param userEntity instance of the user entity
     * @return User domain user object
     */
    public User mapFromUserEntity(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .userName(userEntity.getUserName())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .address(userEntity.getAddress())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }

}
