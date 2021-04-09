package com.example.electronico.constant;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 86400000; //1 DAY in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified.";
    public static final String ISSUER = "ELECTRONICO";
    public static final String ELECTRONICO_ADMINISTRATION = "Setup Portal";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/api/user/register", "/api/user/login", "/api/product/get/all"};
}

