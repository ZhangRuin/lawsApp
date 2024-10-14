package com.example.law.service;

import com.example.law.pojo.vo.ocr.OcrResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OcrService {
    OcrResult actionOcr(MultipartFile multipartFile);
    List<OcrResult> actionOcr(MultipartFile[] multipartFiles);
}
