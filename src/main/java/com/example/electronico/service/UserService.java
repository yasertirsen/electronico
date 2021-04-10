package com.example.electronico.service;

import com.example.electronico.exception.*;
import com.example.electronico.model.Cart;
import com.example.electronico.model.User;
import com.example.electronico.model.UserPrincipal;
import com.example.electronico.repository.CartRepository;
import com.example.electronico.repository.UserRepository;
import com.example.electronico.service.pattern.CrudTemplate;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.electronico.constant.ErrorConstants.EMAIL_ALREADY_EXISTS;
import static com.example.electronico.model.Role.ROLE_USER;

@Service
@Qualifier("UserDetailsService")
public class UserService extends CrudTemplate<User> implements UserDetailsService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Autowired
    public UserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, CartRepository cartRepository) {
        super(userRepository);
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public User update(User user) throws NotFoundException {
        if(!userRepository.existsById(user.getUserId()))
            throw new NotFoundException("User not found");
        return userRepository.save(user);
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()
                -> new UserNotFoundException("User with email " + email + " was not found"));
        return new UserPrincipal(user);
    }

    public User register(User user) throws EmailExistsException, UsernameExistsException, ElectronicoException {
        validateEmail(user.getEmail());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setIsLocked(false);
        user.setRole(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        user.setCart(new Cart());

        User registeredUser = add(user);
        Cart cart = registeredUser.getCart();
        cart.setUserId(registeredUser.getUserId());
        cartRepository.save(cart);

        return registeredUser;
    }

    private void validateEmail(String newEmail) throws EmailExistsException {
        if(userRepository.existsByEmail(newEmail)) {
            throw new EmailExistsException(EMAIL_ALREADY_EXISTS);
        }
    }
}
