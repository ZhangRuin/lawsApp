package com.example.law.controller;
import cn.dev33.satoken.stp.StpUtil;
import com.example.law.pojo.vo.common.ResultResponse;
import com.example.law.pojo.vo.legalDoc.LegalDocRequest;
import com.example.law.service.LegalDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/template")
public class LegalDocController {
    @Autowired
    LegalDocService legalDocService;

    @PostMapping("")
    public ResultResponse legalDocAsk(@Valid @RequestBody LegalDocRequest legalDocRequest){
        return ResultResponse.success(legalDocService.legalDocTemplate((String) StpUtil.getLoginId(),legalDocRequest.getAsk()));
    }
}
