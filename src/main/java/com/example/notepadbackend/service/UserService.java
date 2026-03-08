package com.example.notepadbackend.service;

import com.example.notepadbackend.dto.UserCreateRequest;
import com.example.notepadbackend.entity.AuthUser;
import com.example.notepadbackend.repository.AuthUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final AuthUserRepository authUserRepository;

    public UserService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    public AuthUser createUser(UserCreateRequest request) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        AuthUser user = new AuthUser();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setBirthDate(request.getBirthDate());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());

        // hash password
        user.setPassword(encoder.encode(request.getPassword()));

        user.setActive(true);
        user.setDeleted(false);

        // timestamps
        user.setCreatedDateTime(LocalDateTime.now());
        user.setUpdatedDateTime(LocalDateTime.now());

        return authUserRepository.save(user);
    }
}