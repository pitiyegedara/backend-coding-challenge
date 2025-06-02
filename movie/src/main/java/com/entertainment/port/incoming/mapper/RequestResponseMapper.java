package com.entertainment.port.incoming.mapper;

import com.entertainment.domain.movie.core.Movie;
import com.entertainment.domain.movie.core.MovieRating;
import com.entertainment.domain.movie.core.Rating;
import com.entertainment.domain.user.core.User;
import com.entertainment.movie.dto.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
public class RequestResponseMapper {

    /**
     * Maps the api movieDto to domain movie object
     *
     * @param basicMovieDto basic details of the movie received from the api
     * @return domain movie object
     */
    public Movie mapToDomainMovie(BasicMovieDto basicMovieDto) {
        return Movie.builder()
                .title(basicMovieDto.getTitle())
                .description(basicMovieDto.getDescription())
                .producer(basicMovieDto.getProducer())
                .durationInMinutes(basicMovieDto.getDurationInMinutes())
                .movieLanguage(basicMovieDto.getLanguage()).build();
    }

    /**
     * Creates a common response object using the given id and the message
     *
     * @param message response message
     * @param id      ID of the created object
     * @return CommonResponse object
     */
    public CommonResponse mapToCommonResponse(String message, UUID id) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.message(message).id(id.toString());
        return commonResponse;
    }

    /**
     * Maps domain movie object to an API movie dto
     *
     * @param movie domain movie object
     * @return api movie dto
     */
    public ExtendedMovieDto mapToExtendedMovieDto(Movie movie) {
        return new ExtendedMovieDto()
                .id(movie.getId().toString())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .producer(movie.getProducer())
                .language(movie.getMovieLanguage())
                .overallRating(movie.getOverallRating())
                .totalRatings(movie.getTotalRatings() == null ? null : movie.getTotalRatings().intValue())
                .durationInMinutes(movie.getDurationInMinutes());
    }

    /**
     * Maps the rating details received through the API to domain rating object
     *
     * @param rateDto rating details
     * @param userId  the person who gives the rating
     * @param movieId the movie the rate is given
     * @return domain Rating object
     */
    public Rating mapToDomainRating(RateDto rateDto, String userId, String movieId) {
        return Rating.builder().movieId(UUID.fromString(movieId))
                .userId(UUID.fromString(userId))
                .ratingValue(rateDto.getRatingValue())
                .comment(rateDto.getComment()).build();
    }

    /**
     * Maps api user details into a domain user object
     *
     * @param userDto user details received through api
     * @return User domain user details object
     */
    public User mapToDomainUser(UserDto userDto) {
        return User.builder()
                .userName(userDto.getUserName())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .address(userDto.getAddress()).build();
    }

    /**
     * Maps the domain user object to an api user object
     *
     * @param user domain user detail
     * @return ExtendedUserDto api user detail
     */
    public ExtendedUserDto mapToExtendedUser(User user) {
        return new ExtendedUserDto()
                .id(user.getId())
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .address(user.getAddress());
    }

    /**
     * Maps a list of domain movieRatings to a list of api UserRatedMovieDto
     *
     * @param movieRatings domain rating list
     * @return List<UserRatedMovieDto> api rating list
     */
    public List<UserRatedMovieDto> mapToUserRatedMovieDtoList(Set<MovieRating> movieRatings) {
        List<UserRatedMovieDto> userRatedMovieDtoList = new ArrayList<>();
        movieRatings.forEach(movieRating -> {
            UserRatedMovieDto userRatedMovieDto = new UserRatedMovieDto();
            userRatedMovieDto.setTitle(movieRating.getMovieTitle());
            userRatedMovieDto.setId(movieRating.getMovieId().toString());
            userRatedMovieDto.description(movieRating.getMovieDescription());
            userRatedMovieDto.producer(movieRating.getMovieProducer());
            userRatedMovieDto.language(movieRating.getMovieLanguage());
            userRatedMovieDto.durationInMinutes(movieRating.getMovieDuration());
            userRatedMovieDto.setOverallRating(movieRating.getOverallRating());
            userRatedMovieDto.setTotalRatings(movieRating.getTotalRatings().intValue());
            userRatedMovieDto.setUserRating(movieRating.getUserRating());
            userRatedMovieDtoList.add(userRatedMovieDto);
        });
        return userRatedMovieDtoList;
    }
}
