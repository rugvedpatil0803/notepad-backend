package com.example.notepadbackend.repository;

import com.example.notepadbackend.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {

    Optional<Notes> findByTitle(String title);

}