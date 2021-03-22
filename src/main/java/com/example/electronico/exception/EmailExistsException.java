package com.example.electronico.exception;

public class EmailExistsException extends Exception {

    public EmailExistsException() {
    }

    public EmailExistsException(String message) {
        super(message);
    }
}
