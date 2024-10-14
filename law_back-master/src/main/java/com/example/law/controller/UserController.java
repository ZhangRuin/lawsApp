package com.example.law.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.law.dao.UserRepository;
import com.example.law.pojo.entity.User;
import com.example.law.pojo.vo.Issue.CreateNicknameRequest;
import com.example.law.pojo.vo.common.BizException;
import com.example.law.pojo.vo.common.ExceptionEnum;
import com.example.law.pojo.vo.common.ResultResponse;
import com.example.law.pojo.vo.user.AddStaffRequest;
import com.example.law.pojo.vo.user.LoginRequest;
import com.example.law.service.ImageService;
import com.example.law.service.IssueService;
import com.example.law.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private IssueService issueService;

    @Autowired
    ImageService imageService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login/test")
    public ResultResponse loginTest(@Valid @RequestBody LoginRequest loginRequest) {
        return ResultResponse.success(userService.loginTest(loginRequest.getCode()));
    }

    @PostMapping("/login")
    public ResultResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResultResponse.success(userService.login(loginRequest.getCode()));
    }

    @PostMapping("/add/staff")
    public ResultResponse addStaff(@Valid @RequestBody AddStaffRequest addStaffRequest) {
        userService.addStaff(addStaffRequest.getUserId(), addStaffRequest.getStaffPassword());
        return ResultResponse.success("添加工作人员成功");
    }

    //判断用户是否是第一次进入小程序,即是否有昵称和头像
    @GetMapping
    public ResultResponse whetherHasNicknameOrHeadShot() {
        userService.hasNickname((String) StpUtil.getLoginId());
        return ResultResponse.success("用户信息不完善，请进行取昵称或头像上传操作");
    }

    //第一次进入小程序时，要求用户取昵称和头像

    //取昵称
    @PostMapping("/nickName")
    public ResultResponse createNickname(@Valid @RequestBody CreateNicknameRequest createNicknameRequest) {
        User user = issueService.createNickname((String) StpUtil.getLoginId(), createNicknameRequest.getNickName());
        return ResultResponse.success(user);
    }

    //修改昵称
    @PostMapping("/editNickName")
    public ResultResponse editNickName(@Valid @RequestBody CreateNicknameRequest createNicknameRequest) {
        User user = issueService.createNickname((String) StpUtil.getLoginId(), createNicknameRequest.getNickName());
        return ResultResponse.success(user);
    }

    //上传头像或修改头像
    @PostMapping("/uploadHeadshot")
    public ResultResponse uploadHeadshot(@RequestParam(value = "file") MultipartFile file) {
        String imageName = imageService.upload(file);
        User user = userRepository.findByUserId((String) StpUtil.getLoginId());
        user.setProfileImage(imageName);
        userRepository.save(user);
        return  ResultResponse.success("imageName: " + imageName);
    }
}
