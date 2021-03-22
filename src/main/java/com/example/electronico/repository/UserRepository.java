package com.example.electronico.repository;

import com.example.electronico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String email);
    Optional<User> findByToken(String token);
    Optional<User> findByEmail(String email);
}
