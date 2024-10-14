package com.example.law.service;

import com.example.law.pojo.vo.user.LoginVO;

public interface UserService {
    LoginVO login(String code);

    LoginVO loginTest(String code);

    void addStaff(String userId, String staffPassword);

    void hasNickname(String userId);
}
