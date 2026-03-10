package com.example.notepadbackend.service;

import com.example.notepadbackend.dto.NotesRequest;
import com.example.notepadbackend.dto.NoteResponse;
import com.example.notepadbackend.entity.AuthUser;
import com.example.notepadbackend.entity.Notes;
import com.example.notepadbackend.entity.NotesUserMapping;
import com.example.notepadbackend.repository.AuthUserRepository;
import com.example.notepadbackend.repository.NotesRepository;
import com.example.notepadbackend.repository.NotesUserMappingRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

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

    // Create Note
    @Transactional
    public String createNote(NotesRequest request, String username) {

        AuthUser user = authUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notes note = new Notes();
        note.setTitle(request.getNoteTitle());
        note.setDescription(request.getNoteContent());
        note.setActive(true);
        note.setDeleted(false);
        note.setCreatedDateTime(LocalDateTime.now());
        note.setUpdatedDateTime(LocalDateTime.now());

        Notes savedNote = notesRepository.save(note);

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

    // Update Note
    @Transactional
    public String updateNote(Long id, NotesRequest request, String username) {

        AuthUser user = authUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        NotesUserMapping mapping = mappingRepository
                .findByUser_IdAndNotes_IdAndActiveTrueAndDeletedFalse(user.getId(), id)
                .orElseThrow(() -> new RuntimeException("You are not authorized to update this note"));

        Notes note = mapping.getNotes();

        note.setTitle(request.getNoteTitle());
        note.setDescription(request.getNoteContent());
        note.setUpdatedDateTime(LocalDateTime.now());

        notesRepository.save(note);

        return "Note updated successfully with id " + note.getId();
    }

    // Delete Note (Soft Delete)
    @Transactional
    public String deleteNote(Long id, String username) {

        AuthUser user = authUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        NotesUserMapping mapping = mappingRepository
                .findByUser_IdAndNotes_IdAndActiveTrueAndDeletedFalse(user.getId(), id)
                .orElseThrow(() -> new RuntimeException("You are not authorized to delete this note"));

        Notes note = mapping.getNotes();

        note.setDeleted(true);
        note.setUpdatedDateTime(LocalDateTime.now());

        notesRepository.save(note);

        return "Note deleted successfully with id " + id;
    }

    // Get all notes for logged user
    public List<Map<String, Object>> getNotesListForUser(Long userId) {

        List<NotesUserMapping> mappings =
                mappingRepository.findByUser_IdAndActiveTrueAndDeletedFalse(userId);

        List<Map<String, Object>> notesList = new ArrayList<>();

        for (NotesUserMapping mapping : mappings) {

            Notes note = mapping.getNotes();

            if (note.isActive() && !note.isDeleted()) {

                Map<String, Object> data = new HashMap<>();
                data.put("id", note.getId());
                data.put("title", note.getTitle());

                notesList.add(data);
            }
        }

        return notesList;
    }

    // Get Note by ID (with authorization check)
    public NoteResponse getNoteById(Long id, String username) {

        AuthUser user = authUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        NotesUserMapping mapping = mappingRepository
                .findByUser_IdAndNotes_IdAndActiveTrueAndDeletedFalse(user.getId(), id)
                .orElseThrow(() -> new RuntimeException("You are not authorized to view this note"));

        Notes note = mapping.getNotes();

        return new NoteResponse(
                note.getTitle(),
                note.getDescription()
        );
    }
}