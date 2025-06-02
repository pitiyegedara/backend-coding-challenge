package com.entertainment.port.incoming;

import com.entertainment.config.TestExtension;
import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.ExtendedUserDto;
import com.entertainment.movie.dto.UserDto;
import com.entertainment.port.outgoing.db.entity.UserEntity;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({TestExtension.class})
@DirtiesContext
public class UserApiIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("should successfully create a user, when all inputs are valid")
    void shouldSuccessfullyCreateUser() {

        UserDto userDto = new UserDto().userName("testUser").firstName("John")
                .lastName("doe").email("test@test.com").address("residence address");
        HttpEntity<UserDto> requestEntity = new HttpEntity<>(userDto);

        ResponseEntity<CommonResponse> createUserResponse = this.restTemplate.exchange(
                "http://localhost:" + port + "/user",
                HttpMethod.POST,
                requestEntity,
                CommonResponse.class
        );

        Assertions.assertEquals(HttpStatus.CREATED, createUserResponse.getStatusCode());
        Assertions.assertNotNull(createUserResponse.getBody());
        Assertions.assertEquals("successfully created", createUserResponse.getBody().getMessage());
        List<UserEntity> users = userRepository.findAll();
        Assertions.assertEquals(1, users.size());
        Assertions.assertEquals(createUserResponse.getBody().getId(), users.getFirst().getId().toString());

        UserEntity expectedDBState = UserEntity.builder()
                .userName(userDto.getUserName())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .address(userDto.getAddress()).build();

        assertThat(users.getFirst())
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(expectedDBState);
    }

    @Test
    @DisplayName("should return a 400 error, when the request is invalid")
    void shouldReturn400ErrorWhenInputIsInvalid() {

        UserDto userDto = new UserDto().userName("testUser")
                .lastName("doe").email("test@test.com").address("residence address");
        HttpEntity<UserDto> requestEntity = new HttpEntity<>(userDto);

        ResponseEntity<CommonResponse> createUserResponse = this.restTemplate.exchange(
                "http://localhost:" + port + "/user",
                HttpMethod.POST,
                requestEntity,
                CommonResponse.class
        );

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, createUserResponse.getStatusCode());
        Assertions.assertNotNull(createUserResponse.getBody());
        Assertions.assertEquals("firstName: must not be null", createUserResponse.getBody().getMessage());
    }

    @Test
    @DisplayName("should return a 208, when the user name already exist")
    void shouldReturn208WhenUserNameAlreadyExist() {

        UserDto userDto = new UserDto().userName("testUser").firstName("John")
                .lastName("doe").email("test@test.com").address("residence address");
        HttpEntity<UserDto> requestEntity = new HttpEntity<>(userDto);

        UserEntity userEntity = UserEntity.builder()
                .userName(userDto.getUserName())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .address(userDto.getAddress())
                .createdAt(new Date())
                .updatedAt(new Date()).build();
        userRepository.save(userEntity);

        ResponseEntity<CommonResponse> createUserResponse = this.restTemplate.exchange(
                "http://localhost:" + port + "/user",
                HttpMethod.POST,
                requestEntity,
                CommonResponse.class
        );

        Assertions.assertEquals(HttpStatus.ALREADY_REPORTED, createUserResponse.getStatusCode());
        Assertions.assertNotNull(createUserResponse.getBody());
        Assertions.assertEquals("Username : testUser, already exist", createUserResponse.getBody().getMessage());
    }

    @Test
    @DisplayName("should return the user object, when an existing id is provided")
    void shouldReturnTheUserObject() {

        UserEntity userEntity = UserEntity.builder()
                .userName("testUser")
                .firstName("John")
                .lastName("doe")
                .email("test@test.com")
                .address("residence address")
                .createdAt(new Date())
                .updatedAt(new Date()).build();
        userRepository.save(userEntity);

        ExtendedUserDto expectedUserDto = new ExtendedUserDto()
                .id(userEntity.getId())
                .userName(userEntity.getUserName())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .address(userEntity.getAddress());

        ResponseEntity<ExtendedUserDto> getUserResponse = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/user/" + userEntity.getId().toString(),
                ExtendedUserDto.class
        );
        Assertions.assertEquals(HttpStatus.OK, getUserResponse.getStatusCode());
        Assertions.assertNotNull(getUserResponse.getBody());
        assertThat(getUserResponse.getBody())
                .usingRecursiveComparison()
                .isEqualTo(expectedUserDto);
    }

    @Test
    @DisplayName("should return 404 error, when an un-existing id is provided")
    void shouldReturn404ErrorWhenAnUnexistingIdIsProvided() {

        var userId = UUID.randomUUID();
        ResponseEntity<CommonResponse> getUserResponse = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/user/" + userId,
                CommonResponse.class
        );
        Assertions.assertEquals(HttpStatus.NOT_FOUND, getUserResponse.getStatusCode());
        Assertions.assertNotNull(getUserResponse.getBody());
        Assertions.assertEquals("Could not find user for : " + userId + ".",
                getUserResponse.getBody().getMessage());
    }
}
