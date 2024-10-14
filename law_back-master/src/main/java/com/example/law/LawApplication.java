package com.example.law;

import cn.dev33.satoken.SaManager;
import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.law.dao.DisputeQuestionRepository;
import com.example.law.dao.LawQuestionRepository;
import com.example.law.dao.LinkOfLegalDocRepository;
import com.example.law.pojo.entity.DisputeQuestion;
import com.example.law.pojo.entity.LawQuestion;
import com.example.law.pojo.entity.LinkOfLegalDoc;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.InputStream;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class LawApplication {
    @Value("${enable-init-data}")
    private boolean enableInitData;

    public static void main(String[] args) throws JsonProcessingException {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(LawApplication.class, args);
        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
    }

    @Bean
    public CommandLineRunner dataInit(LawQuestionRepository lawQuestionRepository,
                                      DisputeQuestionRepository disputeQuestionRepository,
                                      LinkOfLegalDocRepository linkOfLegalDocRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                if (!enableInitData) {
                    log.info("init data disabled");
                    return;
                }
                log.info("init data start");

                InputStream inputStream1 = new ClassPathResource("data1.json").getInputStream();
                JSONArray jsonArray1 = JSONArray.parseArray(IoUtil.readUtf8(inputStream1));
                for (int i = 0; i < jsonArray1.size(); i++) {
                    LawDisputeQuestionInit dataInit = JSONObject.toJavaObject(jsonArray1.getJSONObject(i), LawDisputeQuestionInit.class);
                    lawQuestionRepository.save(LawQuestion.builder().aiTitle(dataInit.getAiTitle())
                            .question(dataInit.getQuestion()).answer(dataInit.getAnswer()).questionNum(0).build());
                }

                InputStream inputStream2 = new ClassPathResource("data2.json").getInputStream();
                JSONArray jsonArray2 = JSONArray.parseArray(IoUtil.readUtf8(inputStream2));
                for (int i = 0; i < jsonArray2.size(); i++) {
                    LawDisputeQuestionInit dataInit = JSONObject.toJavaObject(jsonArray2.getJSONObject(i), LawDisputeQuestionInit.class);
                    disputeQuestionRepository.save(DisputeQuestion.builder().aiTitle(dataInit.getAiTitle())
                            .question(dataInit.getQuestion()).answer(dataInit.getAnswer()).questionNum(0).build());
                }

                InputStream inputStream3 = new ClassPathResource("legalDocData.json").getInputStream();
                JSONArray jsonArray3 = JSONArray.parseArray(IoUtil.readUtf8(inputStream3));
                for (int i = 0; i < jsonArray3.size(); i++) {
                    docLinkInit dataInit = JSONObject.toJavaObject(jsonArray3.getJSONObject(i), docLinkInit.class);
                    linkOfLegalDocRepository.save(LinkOfLegalDoc.builder().category(dataInit.getCategory())
                            .link(dataInit.getLink()).build());
                }
                log.info("init data end");
            }
        };
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @Builder
    public static class LawDisputeQuestionInit {
        private String aiTitle;
        private String question;
        private String answer;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @Builder
    public static class docLinkInit {
        private String category;
        private String link;
    }
}

