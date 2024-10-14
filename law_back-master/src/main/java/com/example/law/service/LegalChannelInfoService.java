package com.example.law.service;

import com.example.law.pojo.entity.LegalChannelInfo;

import java.util.List;

public interface LegalChannelInfoService {
    List<LegalChannelInfo> getChannelsByType(String type);

    List<LegalChannelInfo> searchByKeyword(String keyword);

    List<LegalChannelInfo> searchByAddress(String address);

    LegalChannelInfo fillInChannelInfo(String type,String name,String contact,String address,String description);


}
