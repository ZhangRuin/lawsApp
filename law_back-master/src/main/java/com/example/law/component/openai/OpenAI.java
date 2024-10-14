package com.example.law.component.openai;

import com.alibaba.fastjson.JSONObject;
import com.example.law.config.RestTemplateConfig;
import com.example.law.pojo.vo.common.BizException;
import com.example.law.pojo.vo.common.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Component
@Slf4j
public class OpenAI {
    @Value("${openai.apiKey}")
    private String apiKey;
    @Value("${openai.hostUrl}")
    private String hostUrl;
    @Resource(name = "restTemplateHttps")
    private RestTemplate restTemplate;

    public String askGPT(Request request) {
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

        String requestBody = JSONObject.toJSONString(request);
        // 创建请求实体对象
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ChatCompletionResponse chatCompletionResponse = restTemplate.postForObject(hostUrl, entity, ChatCompletionResponse.class);
        if (chatCompletionResponse != null) {
            return chatCompletionResponse.getChoices().get(0).getMessage().getContent();
        } else {
            throw new BizException(ExceptionEnum.AI_ANSWER_ERROR);
        }
    }

}
