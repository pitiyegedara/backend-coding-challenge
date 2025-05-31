package com.entertainment.movie.port.incoming;


import com.entertainment.movie.api.MovieApi;
import com.entertainment.movie.domain.service.MovieService;
import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.MovieDto;
import com.entertainment.movie.port.incoming.mapper.RequestResponseMapper;
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
    public ResponseEntity<CommonResponse> create(MovieDto movieDto) throws Exception {
        var domainMovie = requestResponseMapper.mapToDomainMovie(movieDto);
        movieService.createMovie(domainMovie);
        return new ResponseEntity<>(requestResponseMapper.mapToCommonResponse(domainMovie), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MovieDto> view(String movieId) throws Exception {
        var movie = movieService.viewMovie(UUID.fromString(movieId));
        return new ResponseEntity<>(requestResponseMapper.mapToMovieDto(movie), HttpStatus.OK);
    }
}
