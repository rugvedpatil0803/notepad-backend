package com.example.notepadbackend.controller;

import com.example.notepadbackend.dto.UserCreateRequest;
import com.example.notepadbackend.entity.AuthUser;
import com.example.notepadbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/create_user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public AuthUser createUser(@RequestBody UserCreateRequest request) {

        return userService.createUser(request);
    }
}
