package com.example.notepadbackend.controller;

import com.example.notepadbackend.dto.NotesRequest;
import com.example.notepadbackend.dto.NoteResponse;
import com.example.notepadbackend.service.NotesService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    // Create Note
    @PostMapping("/create_note")
    public String createNote(@RequestBody NotesRequest request) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return notesService.createNote(request, username);
    }

    // Get Note by id
    @GetMapping("/{id}")
    public NoteResponse getNote(@PathVariable Long id) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return notesService.getNoteById(id, username);
    }

    // Update Note
    @PutMapping("/{id}")
    public String updateNote(@PathVariable Long id,
                             @RequestBody NotesRequest request) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return notesService.updateNote(id, request, username);
    }

    // Delete Note
    @DeleteMapping("/{id}")
    public String deleteNote(@PathVariable Long id) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return notesService.deleteNote(id, username);
    }
}