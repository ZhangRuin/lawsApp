package com.example.law.component;

import com.example.law.pojo.vo.user.WxLoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
public class WxLoginClient {
    @Resource(name="restTemplate")
    private RestTemplate restTemplate;
    private static final String url = "https://api.weixin.qq.com/sns/jscode2session?appid={APPID}&secret={SECRET}&js_code={code}&grant_type=authorization_code";
    private static final String APPID = "wx6b02714d869ba296";
    private static final String SECRET = "773447214870cd20943c4baaf307e95f";

    public ResponseEntity<String> wxLogin(String code) {
        return restTemplate.getForEntity(url, String.class, APPID, SECRET, code);
    }
}
