package com.example.law.service.impl;

import com.baidu.aip.speech.AipSpeech;
import com.example.law.pojo.vo.speech.AipResult;
import com.example.law.service.SpeechService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class SpeechServiceImpl implements SpeechService {
    @Autowired
    private AipSpeech aipSpeech;

    @Override
    public List<String> speech2Text(MultipartFile file) {
        try {
            JSONObject pcm = aipSpeech.asr(file.getBytes(), "pcm", 16000, null);
            AipResult aipResult = com.alibaba.fastjson.JSONObject.parseObject(pcm.toString(), AipResult.class);
            System.out.println(aipResult.getResult());
            return aipResult.getResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
