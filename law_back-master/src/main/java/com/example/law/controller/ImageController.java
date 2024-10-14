package com.example.law.controller;

import com.example.law.pojo.vo.common.ResultResponse;
import com.example.law.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/image")
public class ImageController {
    @Autowired
    ImageService imageService;

    @PostMapping("/upload")
    public ResultResponse upload(MultipartFile file) {
        return ResultResponse.success(imageService.upload(file));
    }

    @GetMapping("/view")
    public void viewImage(@RequestParam String fileName, HttpServletResponse response) {
        imageService.viewImage(fileName, response);

    }

    @GetMapping("/download")
    @ResponseBody
    public void getUrlDownload(@RequestParam String fileName, HttpServletResponse response) {
        imageService.getUrlDownload(fileName, response);
    }

    @DeleteMapping("/delete")
    public ResultResponse deleteImage(@RequestParam String fileName) {
        imageService.deleteImage(fileName);
        return ResultResponse.success();
    }
}


