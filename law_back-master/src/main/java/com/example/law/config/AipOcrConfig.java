package com.example.law.config;

import com.baidu.aip.ocr.AipOcr;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AipOcrConfig {
    private static final String APP_ID = "53166996";
    private static final String API_KEY = "SJXtFb7sIKmVRsTtVqbwsyKU";
    private static final String SECRET_KEY = "22hS48P2xI2OWxGZtemZ7teFo4rROL6H";

    @Bean
    public AipOcr aipOcr() {
        return new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    }
}
