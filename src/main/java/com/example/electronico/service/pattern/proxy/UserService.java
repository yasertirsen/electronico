package com.example.electronico.service.pattern.proxy;

import com.example.electronico.exception.ElectronicoException;
import com.example.electronico.exception.EmailExistsException;
import com.example.electronico.exception.UsernameExistsException;
import com.example.electronico.model.User;

public interface UserService {
    User register(User user) throws EmailExistsException, UsernameExistsException, ElectronicoException;
    void validateEmail(String newEmail) throws EmailExistsException;
}
