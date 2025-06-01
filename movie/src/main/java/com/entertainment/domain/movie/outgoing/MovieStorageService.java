package com.entertainment.domain.movie.outgoing;

import com.entertainment.domain.movie.core.Movie;

import java.util.UUID;

public interface MovieStorageService {

    void saveMovie(Movie movie);

    Movie viewMovie(UUID id);

}
