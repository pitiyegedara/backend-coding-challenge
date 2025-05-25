package com.entertainment.movie.port.incoming;


import com.entertainment.movie.api.MovieApi;
import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController implements MovieApi {

    @Override
    public ResponseEntity<CommonResponse> create(Movie movie) throws Exception {
        return MovieApi.super.create(movie);
    }

    @Override
    public ResponseEntity<Movie> view(String movieId) throws Exception {
        return MovieApi.super.view(movieId);
    }
}
