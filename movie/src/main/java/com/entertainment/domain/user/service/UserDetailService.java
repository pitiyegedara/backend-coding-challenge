package com.entertainment.domain.user.service;

import com.entertainment.domain.user.core.User;
import com.entertainment.domain.user.exception.UserAlreadyExistException;
import com.entertainment.domain.user.outgoing.UserStorageService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailService {

    private final UserStorageService userStorageService;

    public UserDetailService(UserStorageService userStorageService) {
        this.userStorageService = userStorageService;
    }

    public void createUser(User user) {
        if (userStorageService.isUserNameAlreadyExist(user.getUserName())) {
            throw new UserAlreadyExistException("Username : " + user.getUserName() + ", already exist");
        }
        if (userStorageService.isEmailAddressAlreadyExist(user.getEmail())) {
            throw new UserAlreadyExistException("Email address : " + user.getEmail() + ", already exist");
        }
        userStorageService.createUser(user);
    }

    public User viewUserById(UUID userId) {
        return userStorageService.findById(userId);
    }
}
