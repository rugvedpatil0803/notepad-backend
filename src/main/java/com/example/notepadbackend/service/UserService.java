package com.example.notepadbackend.service;

import com.example.notepadbackend.dto.UserCreateRequest;
import com.example.notepadbackend.entity.AuthUser;
import com.example.notepadbackend.repository.AuthUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.notepadbackend.dto.LoginRequest;
import com.example.notepadbackend.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final AuthUserRepository authUserRepository;

    public UserService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    public String createUser(UserCreateRequest request) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        AuthUser user = new AuthUser();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setBirthDate(request.getBirthDate());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());

        user.setPassword(encoder.encode(request.getPassword()));

        user.setActive(true);
        user.setDeleted(false);

        user.setCreatedDateTime(LocalDateTime.now());
        user.setUpdatedDateTime(LocalDateTime.now());

        authUserRepository.save(user);

        return "User Created Successfully !";
    }

    public String loginUser(LoginRequest request) {

        AuthUser user = authUserRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        user.setLastLoggedIn(LocalDateTime.now());
        authUserRepository.save(user);

        return JwtUtil.generateToken(user.getUsername());
    }
}