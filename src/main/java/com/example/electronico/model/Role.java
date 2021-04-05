package com.example.electronico.model;

import lombok.Getter;

import static com.example.electronico.constant.Authority.*;

@Getter
public enum Role {

    ROLE_USER(USER),
    ROLE_ADMIN(ADMIN);

    private final String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }

}
