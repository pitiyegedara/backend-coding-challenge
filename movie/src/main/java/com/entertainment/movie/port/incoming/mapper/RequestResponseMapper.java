package com.entertainment.movie.port.incoming.mapper;

import com.entertainment.movie.domain.service.core.Movie;
import com.entertainment.movie.dto.BasicMovieDto;
import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.ExtendedMovieDto;
import org.springframework.stereotype.Component;

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
     * Maps domain movie object to an API common response
     *
     * @param movie domain movie object
     * @return api common response
     */
    public CommonResponse mapToCommonResponse(Movie movie) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.message("successfully created").id(movie.getId().toString());
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
}
