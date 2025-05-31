package com.entertainment.movie.port.incoming.mapper;

import com.entertainment.movie.domain.service.core.Movie;
import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.MovieDto;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {

    public Movie mapToDomainMovie(MovieDto movieDto) {
        return new Movie()
                .setTitle(movieDto.getTitle())
                .setDescription(movieDto.getDescription())
                .setProducer(movieDto.getProducer())
                .setDurationInMinutes(movieDto.getDurationInMinutes())
                .setCoverImage(movieDto.getCoverImage())
                .setMovieLanguage(movieDto.getLanguage());
    }

    public CommonResponse fromMovieToCommonResponse(Movie movie) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.message("successfully created").id(movie.getId().toString());
        return commonResponse;
    }
}
