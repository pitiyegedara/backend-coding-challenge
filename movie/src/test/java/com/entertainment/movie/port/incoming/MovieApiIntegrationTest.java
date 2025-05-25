package com.entertainment.movie.port.incoming;

import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createMovieIntegrationTest() {
        HttpEntity<Movie> requestEntity = new HttpEntity<>(
                new Movie()
                        .title("hello Jimmy")
                        .description("jimmy's life story")
                        .coverImage("jimmy.png")
                        .producer("timmy")
        );
        ResponseEntity<CommonResponse> commonResponse = this.restTemplate.exchange(
                "http://localhost:" + port + "/movie",
                HttpMethod.POST,
                requestEntity,
                CommonResponse.class
        );
        Assertions.assertNotNull(Objects.requireNonNull(commonResponse.getBody()).getId());
    }


}
