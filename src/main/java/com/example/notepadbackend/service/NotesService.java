package com.example.notepadbackend.service;

import com.example.notepadbackend.dto.NotesRequest;
import com.example.notepadbackend.entity.AuthUser;
import com.example.notepadbackend.entity.Notes;
import com.example.notepadbackend.entity.NotesUserMapping;
import com.example.notepadbackend.repository.AuthUserRepository;
import com.example.notepadbackend.repository.NotesRepository;
import com.example.notepadbackend.repository.NotesUserMappingRepository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotesService {

    private final NotesRepository notesRepository;
    private final NotesUserMappingRepository mappingRepository;
    private final AuthUserRepository authUserRepository;

    public NotesService(
            NotesRepository notesRepository,
            NotesUserMappingRepository mappingRepository,
            AuthUserRepository authUserRepository) {

        this.notesRepository = notesRepository;
        this.mappingRepository = mappingRepository;
        this.authUserRepository = authUserRepository;
    }

    @Transactional
    public String createNote(NotesRequest request){

        //Note Creation
        Notes note = new Notes();
        note.setTitle(request.getNoteTitle());
        note.setDescription(request.getNoteContent());

        note.setActive(true);
        note.setDeleted(false);
        note.setCreatedDateTime(LocalDateTime.now());
        note.setUpdatedDateTime(LocalDateTime.now());

        Notes savedNote = notesRepository.save(note);

        // Mapping Of User and Note
        AuthUser user = authUserRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
        NotesUserMapping mapping = new NotesUserMapping();
        mapping.setUser(user);
        mapping.setNotes(savedNote);

        mapping.setActive(true);
        mapping.setDeleted(false);
        mapping.setCreatedDateTime(LocalDateTime.now());
        mapping.setUpdatedDateTime(LocalDateTime.now());

        mappingRepository.save(mapping);

        return "Note saved successfully with id " + savedNote.getId();
    }
}