package com.example.notepadbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tbl_auth_user")
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_active")
    private boolean active;
    @Column(name = "is_deleted")
    private boolean deleted;

    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;
    @Column(name = "updated_date_time")
    private LocalDateTime updatedDateTime;

    @Column(name = "last_logged_in")
    private LocalDateTime lastLoggedIn;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;

    private String password;
}
