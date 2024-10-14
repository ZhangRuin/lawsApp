package com.example.law.controller;

import com.example.law.pojo.vo.common.ResultResponse;
import com.example.law.service.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {
    @Autowired
    private OcrService ocrService;

    @PostMapping("/file")
    public ResultResponse ocrFile(MultipartFile file) {
        return ResultResponse.success(ocrService.actionOcr(file));
    }

    @PostMapping("/files")
    public ResultResponse ocrFiles(MultipartFile[] files) {
        return ResultResponse.success(ocrService.actionOcr(files));
    }
}
