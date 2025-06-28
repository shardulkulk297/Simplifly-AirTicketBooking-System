package com.ats.simplifly.exception;

public class UsernamAlreadyExistsException extends RuntimeException {
    public UsernamAlreadyExistsException(String message) {
        super(message);
    }
}
