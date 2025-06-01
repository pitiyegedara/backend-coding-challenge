package com.entertainment.domain.user.exception;

public class UserCreationException extends RuntimeException {

    public UserCreationException(String message) {
        super("An error occurred while trying to create the user: " + message);
    }

}
