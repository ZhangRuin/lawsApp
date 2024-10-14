package com.example.law.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONArray;
import com.example.law.dao.LawQuestionRepository;
import com.example.law.pojo.entity.LawQuestion;
import com.example.law.pojo.vo.common.ResultResponse;
import com.example.law.pojo.vo.ocr.OcrResult;
import com.example.law.service.AIService;
import com.example.law.service.ForewarnService;
import com.example.law.service.OcrService;
import com.example.law.service.SpeechService;
import com.example.law.service.impl.OpenAIServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private LawQuestionRepository lawQuestionRepository;
    @Autowired
    private OcrService ocrService;
    @Autowired
    private AIService aiService;
    @Autowired
    private OpenAIServiceImpl openAIService;
    @Autowired
    private ForewarnService forewarnService;
    @Autowired
    private SpeechService speechService;

    @GetMapping("/test1")
    public ResultResponse test1() {
        return ResultResponse.success();
    }

    @PostMapping("/test2")
    public ResultResponse test2(String userId, MultipartFile multipartFile) {
        return ResultResponse.success(forewarnService.conversationForewarn(userId,multipartFile));
    }


    @PostMapping("/test3")
    public ResultResponse test3(String userId, MultipartFile[] multipartFiles) {
        return ResultResponse.success(forewarnService.askContractForewarn(userId,multipartFiles));
    }

    @PostMapping("/test4")
    public ResultResponse test4(MultipartFile file) {
        return ResultResponse.success(speechService.speech2Text(file));
    }

}
