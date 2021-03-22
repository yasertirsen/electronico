package com.example.electronico.exception;

public class UsernameExistsException extends Exception {

    public UsernameExistsException() {
    }

    public UsernameExistsException(String message) {
        super(message);
    }
}
