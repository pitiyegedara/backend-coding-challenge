package com.entertainment.movie.domain.outgoing;

import com.entertainment.movie.domain.service.core.Movie;

import java.util.UUID;

public interface MovieStorageService {

    void saveMovie(Movie movie);

    Movie viewMovie(UUID id);

}
