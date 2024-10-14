package com.example.law.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface ImageService {



    String upload(MultipartFile file);

    void viewImage(String fileName, HttpServletResponse response);

    void getUrlDownload(String fileName, HttpServletResponse response);

    void deleteImage( String fileName);


}
