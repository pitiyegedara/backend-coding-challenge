package com.entertainment.movie.port.incoming.mapper;

import com.entertainment.movie.domain.service.core.Movie;
import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.MovieDto;
import org.springframework.stereotype.Component;

@Component
public class RequestResponseMapper {

    /**
     * Maps the api movieDto to domain movie object
     * @param movieDto api movie data
     * @return domain movie object
     */
    public Movie mapToDomainMovie(MovieDto movieDto) {
        return Movie.builder()
                .title(movieDto.getTitle())
                .description(movieDto.getDescription())
                .producer(movieDto.getProducer())
                .durationInMinutes(movieDto.getDurationInMinutes())
                .movieLanguage(movieDto.getLanguage()).build();
    }

    /**
     * Maps domain movie object to an API common response
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
     * @param movie domain movie object
     * @return api movie dto
     */
    public MovieDto mapToMovieDto(Movie movie) {
        return new MovieDto()
                .id(movie.getId().toString())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .producer(movie.getProducer())
                .language(movie.getMovieLanguage())
                .durationInMinutes(movie.getDurationInMinutes());

    }
}
