package com.entertainment.movie.port.incoming;

import com.entertainment.config.TestExtension;
import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.RateDto;
import com.entertainment.movie.port.outgoing.db.entity.MovieEntity;
import com.entertainment.movie.port.outgoing.db.entity.RatingEntity;
import com.entertainment.movie.port.outgoing.db.repository.MovieRepository;
import com.entertainment.movie.port.outgoing.db.repository.RatingRepository;
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

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({TestExtension.class})
@DirtiesContext
public class RateApiIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    public void beforeEach() {
        ratingRepository.deleteAll();
    }

    @Test
    @DisplayName("should successfully create a rating, when all inputs are valid")
    void shouldSuccessfullyCreateRating() {

        var movieEntity = MovieEntity.builder().title("hello Jimmy").description("jimmy's life story")
                .producer("timmy").durationInMinutes(120).movieLanguage("English").build();
        movieRepository.save(movieEntity);

        RateDto rateDto = new RateDto().ratingValue(5).comment("nice movie");
        HttpEntity<RateDto> requestEntity = new HttpEntity<>(rateDto);

        var userId = UUID.randomUUID();

        ResponseEntity<CommonResponse> createRatingResponse = this.restTemplate.exchange(
                "http://localhost:" + port + "/rate/" + movieEntity.getId() + "/" + userId,
                HttpMethod.POST,
                requestEntity,
                CommonResponse.class
        );

        Assertions.assertEquals(HttpStatus.CREATED, createRatingResponse.getStatusCode());
        Assertions.assertNotNull(createRatingResponse.getBody());
        Assertions.assertEquals("successfully created", createRatingResponse.getBody().getMessage());
        List<RatingEntity> ratings = ratingRepository.findAll();
        Assertions.assertEquals(1, ratings.size());
        Assertions.assertEquals(createRatingResponse.getBody().getId(), ratings.getFirst().getId().toString());

        RatingEntity expectedDBState = RatingEntity.builder()
                .ratingValue(rateDto.getRatingValue())
                .comment(rateDto.getComment())
                .userId(userId)
                .movieId(movieEntity.getId()).build();

        assertThat(ratings.getFirst())
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(expectedDBState);
    }

    @Test
    @DisplayName("should throw 500 error when process failed due to an internal error")
    void shouldThrow500ErrorWhenProcessFailed() {

        RateDto rateDto = new RateDto().ratingValue(5).comment("nice movie");
        HttpEntity<RateDto> requestEntity = new HttpEntity<>(rateDto);

        var userId = UUID.randomUUID();
        var movieId = UUID.randomUUID();

        ResponseEntity<CommonResponse> createRatingResponse = this.restTemplate.exchange(
                "http://localhost:" + port + "/rate/" + movieId + "/" + userId,
                HttpMethod.POST,
                requestEntity,
                CommonResponse.class
        );

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, createRatingResponse.getStatusCode());
        Assertions.assertNotNull(createRatingResponse.getBody());
        Assertions.assertEquals("An error occurred while trying save the rating: save to database failed",
                createRatingResponse.getBody().getMessage());
    }

    @Test
    @DisplayName("should throw a 400 error when invalid request body is provided")
    void shouldReturn400ErrorWhenInvalidRequestBodyIsProvided() {
        RateDto rateDto = new RateDto();
        HttpEntity<RateDto> requestEntity = new HttpEntity<>(rateDto);

        var userId = UUID.randomUUID();
        var movieId = UUID.randomUUID();

        ResponseEntity<CommonResponse> createRatingResponse = this.restTemplate.exchange(
                "http://localhost:" + port + "/rate/" + movieId + "/" + userId,
                HttpMethod.POST,
                requestEntity,
                CommonResponse.class
        );

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, createRatingResponse.getStatusCode());
        Assertions.assertNotNull(createRatingResponse.getBody());
        Assertions.assertEquals("ratingValue: must not be null", createRatingResponse.getBody().getMessage());
    }

    @Test
    @DisplayName("should throw a 400 error when invalid path variables provided")
    void shouldReturn400ErrorWhenInvalidPathVariablesProvided() {
        RateDto rateDto = new RateDto().ratingValue(5).comment("nice movie");
        HttpEntity<RateDto> requestEntity = new HttpEntity<>(rateDto);

        ResponseEntity<CommonResponse> createRatingResponse = this.restTemplate.exchange(
                "http://localhost:" + port + "/rate/movie_id/user_id",
                HttpMethod.POST,
                requestEntity,
                CommonResponse.class
        );

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, createRatingResponse.getStatusCode());
        Assertions.assertNotNull(createRatingResponse.getBody());
        Assertions.assertEquals("parameter `movieId` is invalid", createRatingResponse.getBody().getMessage());
    }
}
