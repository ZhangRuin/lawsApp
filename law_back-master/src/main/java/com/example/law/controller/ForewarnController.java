package com.example.law.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.law.pojo.vo.common.ResultResponse;
import com.example.law.service.AIService;
import com.example.law.service.ForewarnService;
import com.example.law.service.OcrService;
import com.example.law.service.SpeechService;
import com.example.law.service.impl.OpenAIServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/warn")
public class ForewarnController {
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


    @PostMapping("/conversation")
    public String conversationForewarn(MultipartFile multipartFile) {
        String res = forewarnService.conversationForewarn((String) StpUtil.getLoginId(),multipartFile);

        if(res.equals("null")){
            return "抱歉，语音内容过少或录入不清晰，我无法理解您的意思。请提供更多细节，确保录音环境良好";
        }
        return res;
    }


    @PostMapping("/contract")
    public ResultResponse askContractForewarn(MultipartFile[] multipartFiles) {
        return ResultResponse.success(forewarnService.askContractForewarn((String) StpUtil.getLoginId(),multipartFiles));
    }
}
