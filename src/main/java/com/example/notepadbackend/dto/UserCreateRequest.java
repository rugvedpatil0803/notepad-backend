package com.example.notepadbackend.dto;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UserCreateRequest {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String username;
    private String password;

}