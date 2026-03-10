package com.example.notepadbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoteResponse {

    private String noteTitle;
    private String noteContent;

}