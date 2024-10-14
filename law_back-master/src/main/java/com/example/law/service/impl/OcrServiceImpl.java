package com.example.law.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baidu.aip.ocr.AipOcr;
import com.example.law.pojo.vo.common.BizException;
import com.example.law.pojo.vo.common.ExceptionEnum;
import com.example.law.pojo.vo.ocr.OcrResult;
import com.example.law.service.OcrService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class OcrServiceImpl implements OcrService {
    @Autowired
    private AipOcr client;

    @Override
    public OcrResult actionOcr(MultipartFile multipartFile) {
        HashMap<String, String> options = new HashMap<String, String>(4);
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "false");

        // 参数为二进制数组
        byte[] buf = new byte[0];
        try {
            buf = multipartFile.getBytes();
        } catch (IOException e) {
            log.error("获取文件字节数据异常，{}", e.getMessage());
            throw new BizException(ExceptionEnum.FiLE_ERROR);
        }
        JSONObject res = client.basicGeneral(buf, options);
        String jsonData = "";
        try {
            jsonData = res.toString(2);
        } catch (JSONException e) {
            log.error("获取json数据异常，{}", e.getMessage());
            throw new BizException(ExceptionEnum.JSON_DATA_ERROR);
        }
        return JSON.parseObject(jsonData, OcrResult.class);
    }

    @Override
    public List<OcrResult> actionOcr(MultipartFile[] multipartFiles) {
        ArrayList<OcrResult> ocrResults = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            ocrResults.add(actionOcr(multipartFile));
        }
        return ocrResults;
    }
}
