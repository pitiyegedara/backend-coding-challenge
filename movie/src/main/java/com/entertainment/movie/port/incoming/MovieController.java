package com.entertainment.movie.port.incoming;


import com.entertainment.movie.api.MovieApi;
import com.entertainment.movie.domain.service.MovieService;
import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.MovieDto;
import com.entertainment.movie.port.incoming.mapper.RequestMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController implements MovieApi {

    private final RequestMapper requestMapper;
    private final MovieService movieService;

    public MovieController(RequestMapper requestMapper, MovieService movieService) {
        this.requestMapper = requestMapper;
        this.movieService = movieService;
    }

    @Override
    public ResponseEntity<CommonResponse> create(MovieDto movieDto) throws Exception {
        var domainMovie = requestMapper.mapToDomainMovie(movieDto);
        movieService.createMovie(domainMovie);
        return new ResponseEntity<>(requestMapper.fromMovieToCommonResponse(domainMovie), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MovieDto> view(String movieId) throws Exception {
        return MovieApi.super.view(movieId);
    }
}
