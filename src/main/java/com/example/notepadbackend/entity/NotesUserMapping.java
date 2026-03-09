package com.example.notepadbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tbl_notes_user_mapping")
public class NotesUserMapping {

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

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_notes_user")
    )
    private AuthUser user;

    @ManyToOne
    @JoinColumn(
            name = "notes_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_notes_mapping")
    )
    private Notes notes;
}