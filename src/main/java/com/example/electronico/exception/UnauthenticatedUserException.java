package com.example.electronico.exception;

public class UnauthenticatedUserException extends Exception {
    public UnauthenticatedUserException() {
        super();
    }

    public UnauthenticatedUserException(String message) {
        super(message);
    }
}
