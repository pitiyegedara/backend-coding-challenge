package com.entertainment.domain.user.outgoing;

import com.entertainment.domain.user.core.User;

import java.util.UUID;

public interface UserStorageService {

    void createUser(User user);

    boolean isUserNameAlreadyExist(String userName);

    boolean isEmailAddressAlreadyExist(String emailAddress);

    User findById(UUID id);
}
