package com.game.exception;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String username) {
        super("User already exist. Username: " + username);
    }
}
