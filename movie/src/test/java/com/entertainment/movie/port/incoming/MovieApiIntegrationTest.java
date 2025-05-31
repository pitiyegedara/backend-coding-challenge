package com.entertainment.movie.port.incoming;

import com.entertainment.config.TestExtension;
import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.MovieDto;
import com.entertainment.movie.port.outgoing.db.entity.MovieEntity;
import com.entertainment.movie.port.outgoing.db.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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

    @Test
    void shouldSuccessfullyCreateMovie() {

        MovieDto movieDto = new MovieDto().title("hello Jimmy").description("jimmy's life story")
                .coverImage("jimmy.png").producer("timmy").durationInMinutes(120).language("English");
        HttpEntity<MovieDto> requestEntity = new HttpEntity<>(movieDto);
        ResponseEntity<CommonResponse> commonResponse = this.restTemplate.exchange(
                "http://localhost:" + port + "/movie",
                HttpMethod.POST,
                requestEntity,
                CommonResponse.class
        );

        Assertions.assertNotNull(commonResponse.getBody());
        Assertions.assertEquals("successfully created", commonResponse.getBody().getMessage());
        List<MovieEntity> movies = movieRepository.findAll();
        Assertions.assertEquals(1, movies.size());
        Assertions.assertEquals(commonResponse.getBody().getId(), movies.getFirst().getId().toString());

        MovieEntity expectedDBState = new MovieEntity().setMovieLanguage(movieDto.getLanguage())
                .setTitle(movieDto.getTitle())
                .setDescription(movieDto.getDescription()).setCoverImage(movieDto.getCoverImage())
                .setProducer(movieDto.getProducer()).setDurationInMinutes(movieDto.getDurationInMinutes());

        assertThat(movies.getFirst())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedDBState);
    }


}
