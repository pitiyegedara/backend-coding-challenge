package com.entertainment.domain.user.exception;

public class UserDetailAccessException extends RuntimeException {
    public UserDetailAccessException(String message) {
        super("An error occurred while accessing the user: " + message);
    }
}
