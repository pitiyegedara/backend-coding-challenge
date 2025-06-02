package com.entertainment.port.incoming;

import com.entertainment.config.TestExtension;
import com.entertainment.movie.dto.BasicMovieDto;
import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.ExtendedMovieDto;
import com.entertainment.port.outgoing.db.entity.MovieEntity;
import com.entertainment.port.outgoing.db.entity.RatingEntity;
import com.entertainment.port.outgoing.db.repository.MovieRepository;
import com.entertainment.port.outgoing.db.repository.RatingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private RatingRepository ratingRepository;

    @BeforeEach
    public void beforeEach() {
        ratingRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    @DisplayName("should successfully create a movie, when all inputs are valid")
    void shouldSuccessfullyCreateMovie() {

        BasicMovieDto movieDto = new BasicMovieDto().title("hello Jimmy").description("jimmy's life story")
                .producer("timmy").durationInMinutes(120).language("English");
        HttpEntity<BasicMovieDto> requestEntity = new HttpEntity<>(movieDto);

        ResponseEntity<CommonResponse> createMovieResponse = this.restTemplate.exchange(
                "http://localhost:" + port + "/movie",
                HttpMethod.POST,
                requestEntity,
                CommonResponse.class
        );

        Assertions.assertEquals(HttpStatus.CREATED, createMovieResponse.getStatusCode());
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
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(expectedDBState);
    }

    @Test
    @DisplayName("should return a 400 error for the requests with invalid parameters")
    void badRequestErrorShouldBeThrownForInvalidInputs() {

        BasicMovieDto movieDto = new BasicMovieDto().title("hello Jimmy").description("jimmy's life story")
                .producer("timmy").durationInMinutes(120);
        HttpEntity<BasicMovieDto> requestEntity = new HttpEntity<>(movieDto);

        ResponseEntity<CommonResponse> createMovieResponse = this.restTemplate.exchange(
                "http://localhost:" + port + "/movie",
                HttpMethod.POST,
                requestEntity,
                CommonResponse.class
        );

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, createMovieResponse.getStatusCode());
        Assertions.assertNotNull(createMovieResponse.getBody());

        Assertions.assertEquals("language: must not be null",
                createMovieResponse.getBody().getMessage());
        List<MovieEntity> movies = movieRepository.findAll();
        Assertions.assertTrue(movies.isEmpty());

    }

    @Test
    @DisplayName("should return the movie object, when an existing id is provided")
    void shouldReturnTheMovieObject() {

        var movieEntity = MovieEntity.builder().title("hello Jimmy").description("jimmy's life story")
                .producer("timmy").durationInMinutes(120).movieLanguage("English").build();
        movieRepository.save(movieEntity);

        ratingRepository.save(RatingEntity.builder()
                .ratingValue(5)
                .movieId(movieEntity.getId())
                .userId(UUID.randomUUID())
                .build());
        ratingRepository.save(RatingEntity.builder()
                .ratingValue(6)
                .movieId(movieEntity.getId())
                .userId(UUID.randomUUID())
                .build());

        ExtendedMovieDto expectedMovieDto = new ExtendedMovieDto().title(movieEntity.getTitle())
                .description(movieEntity.getDescription())
                .producer(movieEntity.getProducer())
                .durationInMinutes(movieEntity.getDurationInMinutes())
                .language(movieEntity.getMovieLanguage())
                .totalRatings(2)
                .overallRating(BigDecimal.valueOf(5.5))
                .id(movieEntity.getId().toString());

        ResponseEntity<ExtendedMovieDto> getMovieResponse = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/movie/" + movieEntity.getId().toString(),
                ExtendedMovieDto.class
        );
        Assertions.assertEquals(HttpStatus.OK, getMovieResponse.getStatusCode());
        Assertions.assertNotNull(getMovieResponse.getBody());
        assertThat(getMovieResponse.getBody())
                .usingRecursiveComparison()
                .isEqualTo(expectedMovieDto);
    }

    @Test
    @DisplayName("should return an 404 error when an un-existing id is provided")
    void shouldReturnNotFoundHttpStatus() {

        var id = UUID.randomUUID();
        ResponseEntity<CommonResponse> getMovieResponse = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/movie/" + id,
                CommonResponse.class
        );
        Assertions.assertEquals(HttpStatus.NOT_FOUND, getMovieResponse.getStatusCode());
        Assertions.assertNotNull(getMovieResponse.getBody());
        Assertions.assertEquals(getMovieResponse.getBody().getMessage(), "Could not find movie " + id + ".");
    }


}
