package com.entertainment.port.incoming;

import com.entertainment.domain.movie.service.RatingService;
import com.entertainment.domain.user.service.UserDetailService;
import com.entertainment.movie.api.ProfileApi;
import com.entertainment.movie.dto.UserProfileDto;
import com.entertainment.port.incoming.mapper.RequestResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ProfileController implements ProfileApi {

    private final RatingService ratingService;
    private final RequestResponseMapper requestResponseMapper;
    private final UserDetailService userDetailService;

    public ProfileController(
            RatingService ratingService,
            RequestResponseMapper requestResponseMapper,
            UserDetailService userDetailService
    ) {
        this.ratingService = ratingService;
        this.requestResponseMapper = requestResponseMapper;
        this.userDetailService = userDetailService;
    }

    @Override
    public ResponseEntity<UserProfileDto> viewProfile(UUID userId) {

        var user = userDetailService.viewUserById(userId);
        var ratedMovies = ratingService.getRatedMovies(userId);

        var extendedUserDto = requestResponseMapper.mapToExtendedUser(user);
        var ratedMovieList = requestResponseMapper.mapToUserRatedMovieDtoList(ratedMovies);
        return new ResponseEntity<>(
                new UserProfileDto().userDetail(extendedUserDto).ratedMovies(ratedMovieList),
                HttpStatus.OK
        );
    }
}
