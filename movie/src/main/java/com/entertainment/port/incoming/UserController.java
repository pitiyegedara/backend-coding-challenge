package com.entertainment.port.incoming;

import com.entertainment.domain.user.service.UserDetailService;
import com.entertainment.movie.api.UserApi;
import com.entertainment.movie.dto.CommonResponse;
import com.entertainment.movie.dto.ExtendedUserDto;
import com.entertainment.movie.dto.UserDto;
import com.entertainment.port.incoming.mapper.RequestResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController implements UserApi {

    private final RequestResponseMapper requestResponseMapper;
    private final UserDetailService userDetailService;

    public UserController(RequestResponseMapper requestResponseMapper, UserDetailService userDetailService) {
        this.requestResponseMapper = requestResponseMapper;
        this.userDetailService = userDetailService;
    }

    @Override
    public ResponseEntity<CommonResponse> createUser(UserDto userDto) {
        var domainUser = requestResponseMapper.mapToDomainUser(userDto);
        userDetailService.createUser(domainUser);
        return new ResponseEntity<>(
                requestResponseMapper.mapToCommonResponse("successfully created", domainUser.getId()),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ExtendedUserDto> viewUserById(UUID userId) {
        var user = userDetailService.viewUserById(userId);
        return new ResponseEntity<>(requestResponseMapper.mapToExtendedUser(user), HttpStatus.OK);
    }
}
