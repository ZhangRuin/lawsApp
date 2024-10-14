package com.example.law.service;

import org.springframework.web.multipart.MultipartFile;

public interface ForewarnService {

    String askContractForewarn(String userId, MultipartFile[] multipartFiles);

    //返回图片名
    Long uploadContractImages(String userId, MultipartFile[] multipartFiles);

    String conversationForewarn(String userId, MultipartFile multipartFile);

}
