package com.entertainment.port.incoming;

import com.entertainment.config.TestExtension;
import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.ExtendedUserDto;
import com.entertainment.movie.dto.UserProfileDto;
import com.entertainment.movie.dto.UserRatedMovieDto;
import com.entertainment.port.outgoing.db.entity.MovieEntity;
import com.entertainment.port.outgoing.db.entity.RatingEntity;
import com.entertainment.port.outgoing.db.entity.UserEntity;
import com.entertainment.port.outgoing.db.repository.MovieRepository;
import com.entertainment.port.outgoing.db.repository.RatingRepository;
import com.entertainment.port.outgoing.db.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({TestExtension.class})
@DirtiesContext
public class ProfileApiIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        ratingRepository.deleteAll();
        userRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    @DisplayName("should return the correct profile details")
    void shouldSuccessfullyCreateMovie() {

        //Crate three movie records in the database
        var firstMovieEntity = MovieEntity.builder().title("hello Jimmy").description("jimmy's life story")
                .producer("timmy").durationInMinutes(120).movieLanguage("English").build();

        var secondMovieEntity = MovieEntity.builder().title("hello Jimmy 2").description("jimmy's life story 2")
                .producer("timmy").durationInMinutes(125).movieLanguage("English").build();

        var thirdMovieEntity = MovieEntity.builder().title("hello Jimmy 3").description("jimmy's life story 3")
                .producer("timmy").durationInMinutes(110).movieLanguage("English").build();
        movieRepository.save(firstMovieEntity);
        movieRepository.save(secondMovieEntity);
        movieRepository.save(thirdMovieEntity);

        //Create ratings using ten users for the created movies
        List<UUID> userIds = new ArrayList<>();
        int[] firstMovieRatings = new int[]{8, 8, 8, 8, 8, 9, 7, 4, 2, 1};
        int[] secondMovieRating = new int[]{2, 7, 7, 5, 2, 10, 1, 6, 9, 3};
        for (int i = 0; i < 10; i++) {
            UserEntity userEntity = UserEntity.builder()
                    .userName("testUser")
                    .firstName("John")
                    .lastName("doe")
                    .email("test@test.com")
                    .address("residence address")
                    .createdAt(new Date())
                    .updatedAt(new Date()).build();
            userRepository.save(userEntity);
            userIds.add(userEntity.getId());

            // All users provide ratings for the first movie
            ratingRepository.save(RatingEntity.builder()
                    .ratingValue(firstMovieRatings[i])
                    .movieId(firstMovieEntity.getId())
                    .userId(userEntity.getId())
                    .build());
            if (i < 8) {
                // only first 8 users provide the rating for the second movie
                ratingRepository.save(RatingEntity.builder()
                        .ratingValue(secondMovieRating[i])
                        .movieId(secondMovieEntity.getId())
                        .userId(userEntity.getId())
                        .build());
            } else {
                //only two users will provide the rating for the third movie
                ratingRepository.save(RatingEntity.builder()
                        .ratingValue(secondMovieRating[i])
                        .movieId(thirdMovieEntity.getId())
                        .userId(userEntity.getId())
                        .build());
            }
        }

        // validate the api result for the first user
        ResponseEntity<UserProfileDto> getRatedMoviesResponse = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/profile/" + userIds.getFirst(),
                UserProfileDto.class
        );

        List<UserRatedMovieDto> expectedMovieList = new ArrayList<>();
        expectedMovieList.add(getRateDetail(firstMovieEntity, BigDecimal.valueOf(6.3), 10, 8));
        expectedMovieList.add(getRateDetail(secondMovieEntity, BigDecimal.valueOf(5.0), 8, 2));

        Assertions.assertEquals(HttpStatus.OK, getRatedMoviesResponse.getStatusCode());
        Assertions.assertNotNull(getRatedMoviesResponse.getBody());
        assertThat(expectedMovieList.equals(getRatedMoviesResponse.getBody().getRatedMovies())).isTrue();

        // validate the api result for the last user
        getRatedMoviesResponse = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/profile/" + userIds.getLast(),
                UserProfileDto.class
        );
        expectedMovieList.clear();
        expectedMovieList.add(getRateDetail(firstMovieEntity, BigDecimal.valueOf(6.3), 10, 1));
        expectedMovieList.add(getRateDetail(thirdMovieEntity, BigDecimal.valueOf(6.0), 2, 3));

        Assertions.assertEquals(HttpStatus.OK, getRatedMoviesResponse.getStatusCode());
        Assertions.assertNotNull(getRatedMoviesResponse.getBody());
        assertThat(expectedMovieList.equals(getRatedMoviesResponse.getBody().getRatedMovies())).isTrue();
    }

    @Test
    @DisplayName("should only return the user details, if the user has not given any rating")
    void shouldOnlyReturnTheUserDetails() {
        UserEntity userEntity = UserEntity.builder()
                .userName("testUser")
                .firstName("John")
                .lastName("doe")
                .email("test@test.com")
                .address("residence address")
                .createdAt(new Date())
                .updatedAt(new Date()).build();
        userRepository.save(userEntity);

        ResponseEntity<UserProfileDto> getRatedMoviesResponse = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/profile/" + userEntity.getId(),
                UserProfileDto.class
        );

        ExtendedUserDto expectedUserDto = new ExtendedUserDto()
                .userName(userEntity.getUserName())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .address(userEntity.getAddress());

        Assertions.assertEquals(HttpStatus.OK, getRatedMoviesResponse.getStatusCode());
        Assertions.assertNotNull(getRatedMoviesResponse.getBody());
        Assertions.assertTrue(getRatedMoviesResponse.getBody().getRatedMovies().isEmpty());
        assertThat(getRatedMoviesResponse.getBody().getUserDetail())
                .usingRecursiveComparison()
                .isEqualTo(expectedUserDto);

    }

    @Test
    @DisplayName("should return http status 404, if the given user id does not not exist")
    void shouldReturn404IfUserDoesNotExist() {
        var userId = UUID.randomUUID();
        ResponseEntity<CommonResponse> getRatedMoviesResponse = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/profile/" + userId,
                CommonResponse.class
        );

        Assertions.assertEquals(HttpStatus.NOT_FOUND, getRatedMoviesResponse.getStatusCode());
        Assertions.assertNotNull(getRatedMoviesResponse.getBody());
        Assertions.assertEquals("Could not find user for : " + userId + ".",
                getRatedMoviesResponse.getBody().getMessage());

    }

    private UserRatedMovieDto getRateDetail(
            MovieEntity movieEntity,
            BigDecimal overallRating,
            int totalRatings,
            int userRating
    ) {
        UserRatedMovieDto userRatedMovieDto = new UserRatedMovieDto();
        userRatedMovieDto.setTitle(movieEntity.getTitle());
        userRatedMovieDto.setId(movieEntity.getId().toString());
        userRatedMovieDto.description(movieEntity.getDescription());
        userRatedMovieDto.producer(movieEntity.getProducer());
        userRatedMovieDto.language(movieEntity.getMovieLanguage());
        userRatedMovieDto.durationInMinutes(movieEntity.getDurationInMinutes());
        userRatedMovieDto.setOverallRating(overallRating);
        userRatedMovieDto.setTotalRatings(totalRatings);
        userRatedMovieDto.setUserRating(userRating);
        return userRatedMovieDto;
    }

}
