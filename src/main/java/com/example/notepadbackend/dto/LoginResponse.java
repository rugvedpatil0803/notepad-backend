package com.example.notepadbackend.dto;

import java.util.List;
import java.util.Map;

public class LoginResponse {

    private String token;
    private List<Map<String, Object>> notesList;

    public LoginResponse(String token, List<Map<String, Object>> notesList) {
        this.token = token;
        this.notesList = notesList;
    }

    public String getToken() {
        return token;
    }

    public List<Map<String, Object>> getNotesList() {
        return notesList;
    }
}