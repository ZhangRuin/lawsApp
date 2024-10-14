package com.example.law.config;

import com.baidu.aip.speech.AipSpeech;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsrConfig {
    private static final String APP_ID = "55525648";
    private static final String API_KEY = "EeSH2h9uSO9ODRVZUithXuOS";
    private static final String SECRET_KEY = "cCX7k89ngqY77Ol3UqEEHtdll4NTVLCl";

    @Bean
    public AipSpeech aipSpeech() {
        return new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
    }
}
