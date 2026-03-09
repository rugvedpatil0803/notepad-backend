package com.example.notepadbackend.service;

import com.example.notepadbackend.dto.NotesRequest;
import com.example.notepadbackend.entity.AuthUser;
import com.example.notepadbackend.entity.Notes;
import com.example.notepadbackend.entity.NotesUserMapping;
import com.example.notepadbackend.repository.AuthUserRepository;
import com.example.notepadbackend.repository.NotesRepository;
import com.example.notepadbackend.repository.NotesUserMappingRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

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

    public List<Map<String, Object>> get_notes_list_for_user(Long userId){

        List<NotesUserMapping> mappings =
                mappingRepository.findByUserIdAndActiveTrueAndDeletedFalse(userId);

        List<Map<String, Object>> notesList = new ArrayList<>();

        for (NotesUserMapping mapping : mappings) {

            Notes note = mapping.getNotes();

            if(note.isActive() && !note.isDeleted()) {

                Map<String, Object> data = new HashMap<>();
                data.put("id", note.getId());
                data.put("title", note.getTitle());

                notesList.add(data);
            }
        }

        return notesList;
    }

    public Notes getNoteById(Long id) {

        return notesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
    }
}