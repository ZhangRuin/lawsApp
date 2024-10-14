package com.example.law.pojo.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class WxLoginResult {
    private String openid;
    private String session_key;
    private String unionid;
    private String errcode;
    private String errmsg;
}
