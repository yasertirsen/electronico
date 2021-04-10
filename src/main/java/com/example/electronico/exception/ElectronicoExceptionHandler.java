package com.example.electronico.exception;

import com.example.electronico.model.HttpCustomResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

import static com.example.electronico.constant.ErrorConstants.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ElectronicoExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpCustomResponse> productNotFoundException () {
        return createHttpResponse(BAD_REQUEST, PRODUCT_WAS_NOT_FOUND);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<HttpCustomResponse> incorrectPasswordException () {
        return createHttpResponse(BAD_REQUEST, INCORRECT_PASSWORD);
    }

    @ExceptionHandler(UnauthenticatedUserException.class)
    public ResponseEntity<HttpCustomResponse> unauthenticatedUserException () {
        return createHttpResponse(BAD_REQUEST, COULD_NOT_FIND_CURRENT_USER_LOGGED_IN);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<HttpCustomResponse> usernameExistsException() {
        return createHttpResponse(CONFLICT, USERNAME_ALREADY_EXISTS);
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<HttpCustomResponse> emailExistsException() {
        return createHttpResponse(CONFLICT, EMAIL_ALREADY_EXISTS);
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<HttpCustomResponse> illegalStateException() {
        return createHttpResponse(NOT_IMPLEMENTED, UNEXPECTED_VALUE);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpCustomResponse> accessDeniedException() {
        return createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(InvalidDataFormatException.class)
    public ResponseEntity<HttpCustomResponse> invalidDataFormat() {
        return createHttpResponse(BAD_REQUEST, INVALID_DATA_FORMAT);
    }


    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<HttpCustomResponse> invalidCredentials() {
        return createHttpResponse(UNAUTHORIZED, INVALID_CREDENTIALS);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpCustomResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpCustomResponse> internalServerErrorException() {
        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }


    private ResponseEntity<HttpCustomResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpCustomResponse httpCustomResponse = new HttpCustomResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message);

        return new ResponseEntity<>(httpCustomResponse, httpStatus);
    }

}
