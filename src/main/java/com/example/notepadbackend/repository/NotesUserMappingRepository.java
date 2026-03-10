package com.example.notepadbackend.repository;

import com.example.notepadbackend.entity.NotesUserMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface NotesUserMappingRepository extends JpaRepository<NotesUserMapping, Long> {

    Optional<NotesUserMapping> findByUser_IdAndNotes_IdAndActiveTrueAndDeletedFalse(Long userId, Long notesId);

    List<NotesUserMapping> findByUser_IdAndActiveTrueAndDeletedFalse(Long userId);
}