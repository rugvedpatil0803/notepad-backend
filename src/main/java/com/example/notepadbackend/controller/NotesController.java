package com.example.notepadbackend.controller;

import com.example.notepadbackend.dto.NotesRequest;
import com.example.notepadbackend.entity.Notes;
import com.example.notepadbackend.service.NotesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    private final NotesService notesService;

    // Constructor Injection
    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    // Create Note API
    @PostMapping("/create_note")
    public String createNote(@RequestBody NotesRequest request) {
        return notesService.createNote(request);
    }

    @GetMapping("/{id}")
    public Notes getNote(@PathVariable Long id) {
        return notesService.getNoteById(id);
    }

    @PostMapping("/{id}")
    public String updateNote(@RequestBody NotesRequest request) {
        return notesService.createNote(request);
    }

}