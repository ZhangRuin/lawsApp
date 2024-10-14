package com.example.law.controller;

import com.example.law.pojo.vo.common.ResultResponse;
import com.example.law.service.SpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/speech")
public class SpeechController {
    @Autowired
    private SpeechService speechService;

    @PostMapping("/text")
    public ResultResponse speech2Text(MultipartFile file) {
        return ResultResponse.success(speechService.speech2Text(file));
    }
}
