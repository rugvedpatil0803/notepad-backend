package com.example.notepadbackend.repository;

import com.example.notepadbackend.entity.NotesUserMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesUserMappingRepository extends JpaRepository<NotesUserMapping, Long> {

}