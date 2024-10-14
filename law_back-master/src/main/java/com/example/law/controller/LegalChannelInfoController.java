package com.example.law.controller;


import com.example.law.pojo.entity.LegalChannelInfo;
import com.example.law.pojo.vo.common.BizException;
import com.example.law.pojo.vo.common.ExceptionEnum;
import com.example.law.pojo.vo.common.ResultResponse;
import com.example.law.pojo.vo.legalChannel.FillInChannelRequest;
import com.example.law.service.LegalChannelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/channel")
@Validated
public class LegalChannelInfoController {

    @Autowired
    LegalChannelInfoService legalChannelInfoService;

    //分类(律师，法援，仲裁，调解四个板块),得到某种板块下的信息
    @GetMapping("/type")
    public ResultResponse getChannelsByType(@RequestParam String type) {
        return ResultResponse.success(legalChannelInfoService.getChannelsByType(type));
    }

    //提供搜索功能
    @GetMapping("/search")
    public ResultResponse searchByKeyword(@RequestParam String keyword) {
        List<LegalChannelInfo> legalChannelInfos = legalChannelInfoService.searchByKeyword(keyword);
        if(!legalChannelInfos.isEmpty()) {
            return ResultResponse.success(legalChannelInfoService.searchByKeyword(keyword));
        }else{
            throw new BizException(ExceptionEnum.SEARCH_RES_NOT_EXIST);
        }

    }

    //展示用户所在地的相关信息
    @GetMapping("/address")
    public ResultResponse searchByAddress(@RequestParam String address) {
        List<LegalChannelInfo> legalChannelInfos = legalChannelInfoService.searchByAddress(address);
        if(!legalChannelInfos.isEmpty()) {
            return ResultResponse.success(legalChannelInfoService.searchByAddress(address));
        }else{
            throw new BizException(ExceptionEnum.NOINFO_NEAR_ADDRESS);
        }
    }

    //填入一条渠道信息
    @PostMapping("/fillIn")
    public ResultResponse fillInChannelInfo(@Valid @RequestBody FillInChannelRequest request) {
        return ResultResponse.success(legalChannelInfoService.fillInChannelInfo(
                request.getType(),request.getName(),request.getContact(),request.getAddress(),request.getDescription()));
    }
}
