package com.example.law.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.law.component.WxLoginClient;
import com.example.law.dao.UserRepository;
import com.example.law.pojo.entity.User;
import com.example.law.pojo.vo.common.BizException;
import com.example.law.pojo.vo.common.ExceptionEnum;
import com.example.law.pojo.vo.user.LoginVO;
import com.example.law.pojo.vo.user.WxLoginResult;
import com.example.law.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Value("${add-staff-password}")
    private String staffPassword;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WxLoginClient wxLoginClient;

    @Override
    public LoginVO login(String code) {
        ResponseEntity<String> stringResponseEntity = wxLoginClient.wxLogin(code);
        WxLoginResult wxLoginResult = JSONObject.parseObject(stringResponseEntity.getBody(), WxLoginResult.class);
        log.info("wxLoginResult:{}", wxLoginResult);
        if (wxLoginResult.getOpenid() == null) {
            throw new BizException(ExceptionEnum.WX_LOGIN_ERROR);
        }
        String openid = wxLoginResult.getOpenid();
        StpUtil.login(openid);
        User user = userRepository.findByUserId(openid);
        if (user == null) {
            user = userRepository.save(User.builder().userId(openid).role(User.Role.USER).build());
        }
        return LoginVO.builder().userId(user.getUserId()).role(user.getRole().name().toLowerCase(Locale.ROOT))
                .token(StpUtil.getTokenValue()).build();
    }

    @Override
    public LoginVO loginTest(String code) {
        StpUtil.login(code);
        User user = userRepository.findByUserId(code);
        if (user == null) {
            user = userRepository.save(User.builder().userId(code).role(User.Role.USER).build());
        }
        return LoginVO.builder().role(user.getRole().name().toLowerCase(Locale.ROOT))
                .token(StpUtil.getTokenValue()).build();
    }

    @Override
    public void addStaff(String userId, String staffPassword) {
        if (this.staffPassword.equals(staffPassword)) {
            userRepository.save(User.builder().userId(userId).role(User.Role.STAFF).build());
        } else {
            throw new BizException(ExceptionEnum.STAFF_PASSWORD_ERROR);
        }
    }

    @Override
    public void hasNickname(String userId) {
        if (userRepository.findByUserId(userId).getNickName() != null) {
            throw new BizException(ExceptionEnum.NICKNAME_EXIST_ERROR);
        }
        if (userRepository.findByUserId(userId).getProfileImage() != null) {
            throw new BizException(ExceptionEnum.IMAGE_EXIST_ERROR);
        }
    }

}
