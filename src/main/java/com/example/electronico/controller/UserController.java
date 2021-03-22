package com.example.electronico.controller;

import com.example.electronico.exception.*;
import com.example.electronico.jwt.JwtTokenProvider;
import com.example.electronico.model.User;
import com.example.electronico.model.UserPrincipal;
import com.example.electronico.repository.UserRepository;
import com.example.electronico.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static com.example.electronico.constant.SecurityConstants.EXPIRATION_TIME;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) throws EmailExistsException, UsernameExistsException, ElectronicoException {
        return userService.register(user);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<User> login(@RequestBody User user) throws UserNotFoundException, IncorrectPasswordException {

        User loggedUser = userRepository.findByEmail(user.getEmail()).orElseThrow(()
                -> new UserNotFoundException("User with email " + user.getEmail() + " was not found"));
        if(passwordEncoder.matches(user.getPassword(), loggedUser.getPassword())) {
            authenticate(loggedUser.getEmail(), loggedUser.getPassword());
            UserPrincipal userPrincipal = new UserPrincipal(loggedUser);
            loggedUser.setExpiresIn(EXPIRATION_TIME);
            loggedUser.setToken(jwtTokenProvider.generateJwtToken(userPrincipal));

            return new ResponseEntity<>(loggedUser, HttpStatus.OK);
        }
        else
            throw new IncorrectPasswordException();

    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @GetMapping("/verification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) throws UserNotFoundException {
        return userService.verifyAccount(token);
    }

    @PutMapping("/update")
    public User update(@RequestBody User user) throws UserNotFoundException {
        return userService.update(user);
    }
}
