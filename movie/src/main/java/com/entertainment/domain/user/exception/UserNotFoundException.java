package com.entertainment.domain.user.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String uuid) {
        super("Could not find user for : " + uuid + ".");
    }
}
