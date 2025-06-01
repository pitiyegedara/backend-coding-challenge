package com.entertainment.movie.port.incoming.mapper;

import com.entertainment.movie.domain.service.core.Movie;
import com.entertainment.movie.domain.service.core.Rating;
import com.entertainment.movie.dto.BasicMovieDto;
import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.ExtendedMovieDto;
import com.entertainment.movie.dto.RateDto;
import org.springframework.stereotype.Component;

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
     * @param message response message
     * @param id ID of the created object
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
                .durationInMinutes(movie.getDurationInMinutes());
    }

    /**
     * Map the rating details received through the API to domain rating object
     * @param rateDto rating details
     * @param userId the person who gives the rating
     * @param movieId the movie the rate is given
     * @return domain Rating object
     */
    public Rating mapToDomainRating(RateDto rateDto, String userId, String movieId) {
        return Rating.builder().movieId(UUID.fromString(movieId))
                .userId(UUID.fromString(userId))
                .ratingValue(rateDto.getRatingValue())
                .comment(rateDto.getComment()).build();
    }
}
