package com.example.electronico.service;

import com.example.electronico.exception.ElectronicoException;
import com.example.electronico.exception.EmailExistsException;
import com.example.electronico.exception.UserNotFoundException;
import com.example.electronico.exception.UsernameExistsException;
import com.example.electronico.model.Cart;
import com.example.electronico.model.NotificationEmail;
import com.example.electronico.model.User;
import com.example.electronico.model.UserPrincipal;
import com.example.electronico.repository.CartRepository;
import com.example.electronico.repository.UserRepository;
import com.example.electronico.service.interfaces.UserService;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.example.electronico.constant.ErrorConstants.EMAIL_ALREADY_EXISTS;
import static com.example.electronico.model.Role.ROLE_USER;

@Service
@Qualifier("UserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, MailService mailService, UserRepository userRepository,
                           CartRepository cartRepository) {
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public User register(User user) throws EmailExistsException, UsernameExistsException, ElectronicoException {
        validateUsernameAndEmail(user.getEmail());

        String verificationToken = UUID.randomUUID().toString();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setIsLocked(false);
        user.setRole(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        user.setToken(verificationToken);
        user.setCart(new Cart());

        User registeredUser = userRepository.save(user);
        Cart cart = registeredUser.getCart();
        cart.setUserId(registeredUser.getUserId());
        cartRepository.save(cart);


        mailService.sendMail(new NotificationEmail("Account Activation - Electronico",
                user.getEmail(), "Thank you for signing up to Electronico, " +
                "please click the link below to activate your account " +
                "http://localhost:8084/api/user/verification/" + verificationToken));

        return registeredUser;
    }

    private void validateUsernameAndEmail(String newEmail) throws EmailExistsException {
        if(userRepository.existsByEmail(newEmail)) {
            throw new EmailExistsException(EMAIL_ALREADY_EXISTS);
        }
    }

    @Override
    public ResponseEntity<String> verifyAccount(String token) throws UserNotFoundException {
        User user = userRepository.findByToken(token).orElseThrow(()
                -> new UserNotFoundException("User with token " + token + " was not found"));
        user.setEnabled(true);
        userRepository.save(user);

        return new ResponseEntity<>(new Gson().toJson("Account Activated Successfully"), HttpStatus.OK);
    }

    @Override
    public User update(User user) throws UserNotFoundException {
        if(!userRepository.existsById(user.getUserId()))
            throw new UserNotFoundException("User not found");
        return userRepository.save(user);
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()
                -> new UserNotFoundException("User with email " + email + " was not found"));
        return new UserPrincipal(user);
    }
}
