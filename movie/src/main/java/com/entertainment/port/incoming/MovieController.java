package com.entertainment.port.incoming;


import com.entertainment.movie.api.MovieApi;
import com.entertainment.domain.movie.service.MovieService;
import com.entertainment.movie.dto.BasicMovieDto;
import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.ExtendedMovieDto;
import com.entertainment.port.incoming.mapper.RequestResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class MovieController implements MovieApi {

    private final RequestResponseMapper requestResponseMapper;
    private final MovieService movieService;

    public MovieController(RequestResponseMapper requestResponseMapper, MovieService movieService) {
        this.requestResponseMapper = requestResponseMapper;
        this.movieService = movieService;
    }

    @Override
    public ResponseEntity<CommonResponse> createMovie(BasicMovieDto basicMovieDto) {
        var domainMovie = requestResponseMapper.mapToDomainMovie(basicMovieDto);
        movieService.createMovie(domainMovie);
        return new ResponseEntity<>(
                requestResponseMapper.mapToCommonResponse("successfully created", domainMovie.getId()),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ExtendedMovieDto> viewMovieById(UUID movieId) {
        var movie = movieService.viewMovie(movieId);
        return new ResponseEntity<>(requestResponseMapper.mapToExtendedMovieDto(movie), HttpStatus.OK);
    }
}
