package com.example.law.service.impl;

import com.example.law.dao.FornwarnContractRepository;
import com.example.law.dao.FornwarnConversationRepository;
import com.example.law.pojo.entity.ForewarnContract;
import com.example.law.pojo.entity.ForewarnConversation;
import com.example.law.pojo.vo.common.BizException;
import com.example.law.pojo.vo.common.ExceptionEnum;
import com.example.law.pojo.vo.ocr.OcrResult;
import com.example.law.pojo.vo.ocr.Words;
import com.example.law.service.ForewarnService;
import com.example.law.service.OcrService;
import com.example.law.service.SpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ForewarnServiceImpl implements ForewarnService {
    @Autowired
    private ImageServiceImpl imageService;
    @Autowired
    private FornwarnContractRepository fornwarnContractRepository;
    @Autowired
    private FornwarnConversationRepository fornwarnConversationRepository;
    @Autowired
    private OcrService ocrService;
    @Autowired
    private OpenAIServiceImpl openAIService;
    @Autowired
    private SpeechService speechService;


    @Override
    public String askContractForewarn(String userId, MultipartFile[] multipartFiles) {
        //图片转文字
        List<OcrResult> image2Text = ocrService.actionOcr(multipartFiles);
        StringBuilder contractContent = new StringBuilder();

        //得到合同内容
        for (OcrResult ocrResult : image2Text) {
            for (Words words : ocrResult.getWords_result()) {
                contractContent.append(words.getWords());
            }
        }
        //上传图片，保存图片记录
        Long id = uploadContractImages(userId,multipartFiles);
        String aiAnswer = openAIService.askContractWarning(contractContent.toString()).toString();
        ForewarnContract forewarnContract = fornwarnContractRepository.findById(id).get();
        fornwarnContractRepository.save(forewarnContract.setAiAnswer(aiAnswer));
        return aiAnswer;
    }

    @Override
    public Long uploadContractImages(String userId, MultipartFile[] multipartFiles) {
        ArrayList<String> imgPath = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            imgPath.add(imageService.upload(multipartFile));
        }
        ForewarnContract forewarnContract = fornwarnContractRepository.save(
                ForewarnContract.builder().userId(userId).imgPath(imgPath).build());
        return forewarnContract.getId();
    }

    @Override
    public String conversationForewarn(String userId, MultipartFile multipartFile) {
        //语音转文字
        StringBuilder speech2TextRes = new StringBuilder();

        if(multipartFile == null){
            return "null";
        }

        for(String string: speechService.speech2Text(multipartFile)){
            speech2TextRes.append(string);
        }

        if(speech2TextRes.toString().equals("")){
            return "null";
        }
        if(speech2TextRes.toString().equals("null")){
            return "null";
        }

        //askAI
        String aiAnswer = openAIService.askConversationWarning(speech2TextRes.toString());
        ForewarnConversation forewarnConversation = fornwarnConversationRepository.save(
                ForewarnConversation.builder().userId(userId).aiAnswer(aiAnswer).build()
        );

        return aiAnswer;
    }
}
