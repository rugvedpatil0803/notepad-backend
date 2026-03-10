package com.example.notepadbackend.controller;

import com.example.notepadbackend.dto.LoginRequest;
import com.example.notepadbackend.dto.UserCreateRequest;
import com.example.notepadbackend.dto.LoginResponse;
import com.example.notepadbackend.entity.AuthUser;
import com.example.notepadbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private final UserService userService;

    // Constructor Injection
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Create User API
    @PostMapping("/create_user")
    public String createUser(@RequestBody UserCreateRequest request) {
        return userService.createUser(request);
    }

    // Login API
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.loginUser(request);
    }
}