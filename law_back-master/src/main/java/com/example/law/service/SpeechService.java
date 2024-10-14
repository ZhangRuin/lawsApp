package com.example.law.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SpeechService {
    List<String> speech2Text(MultipartFile file);
}
