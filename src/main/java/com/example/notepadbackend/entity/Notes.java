package com.example.notepadbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tbl_notes")
public class Notes {
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

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_starred")
    private boolean starred;

}


