package com.entertainment.movie.port.incoming;

import com.entertainment.config.TestExtension;
import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.MovieDto;
import com.entertainment.movie.port.outgoing.db.entity.MovieEntity;
import com.entertainment.movie.port.outgoing.db.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({TestExtension.class})
@DirtiesContext
public class MovieApiIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MovieRepository movieRepository;

    MovieDto movieDto = new MovieDto().title("hello Jimmy").description("jimmy's life story")
            .producer("timmy").durationInMinutes(120).language("English");
    HttpEntity<MovieDto> requestEntity = new HttpEntity<>(movieDto);

    @BeforeEach
    public void beforeEach() {
        movieRepository.deleteAll();
    }

    @Test
    void shouldSuccessfullyCreateMovie() {
        ResponseEntity<CommonResponse> createMovieResponse = this.restTemplate.exchange(
                "http://localhost:" + port + "/movie",
                HttpMethod.POST,
                requestEntity,
                CommonResponse.class
        );

        Assertions.assertEquals(HttpStatus.CREATED,createMovieResponse.getStatusCode());
        Assertions.assertNotNull(createMovieResponse.getBody());
        Assertions.assertEquals("successfully created", createMovieResponse.getBody().getMessage());
        List<MovieEntity> movies = movieRepository.findAll();
        Assertions.assertEquals(1, movies.size());
        Assertions.assertEquals(createMovieResponse.getBody().getId(), movies.getFirst().getId().toString());

        MovieEntity expectedDBState = MovieEntity.builder()
                .title(movieDto.getTitle())
                .description(movieDto.getDescription())
                .producer(movieDto.getProducer())
                .movieLanguage(movieDto.getLanguage())
                .durationInMinutes(movieDto.getDurationInMinutes()).build();

        assertThat(movies.getFirst())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedDBState);
    }

    @Test
    void shouldReturnTheMovieObject() {

        var movieEntity = MovieEntity.builder().title("hello Jimmy").description("jimmy's life story")
                .producer("timmy").durationInMinutes(120).movieLanguage("English").build();
        movieRepository.save(movieEntity);

        MovieDto expectedMovieDto = new MovieDto().title(movieEntity.getTitle())
                .description(movieEntity.getDescription())
                .producer(movieEntity.getProducer())
                .durationInMinutes(movieEntity.getDurationInMinutes())
                .language(movieEntity.getMovieLanguage())
                .id(movieEntity.getId().toString());

        ResponseEntity<MovieDto> getMovieResponse = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/movie/" + movieEntity.getId().toString(),
                MovieDto.class
        );
        Assertions.assertEquals(HttpStatus.OK, getMovieResponse.getStatusCode());
        Assertions.assertNotNull(getMovieResponse.getBody());
        assertThat(getMovieResponse.getBody())
                .usingRecursiveComparison()
                .isEqualTo(expectedMovieDto);
    }


}
