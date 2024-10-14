package com.example.law.service.impl;

import com.example.law.dao.LegalChannelInfoRepository;
import com.example.law.pojo.entity.LegalChannelInfo;
import com.example.law.service.LegalChannelInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LegalChannelInfoImpl implements LegalChannelInfoService {

    @Autowired
    LegalChannelInfoRepository legalChannelInfoRepository;

    @Override
    public List<LegalChannelInfo> getChannelsByType(String type) {
        return legalChannelInfoRepository.findByType(type);
    }

    @Override
    public List<LegalChannelInfo> searchByKeyword(String keyword) {
        return legalChannelInfoRepository.findByNameLikeOrContactLikeOrAddressLikeOrDescriptionLike(
                "%" + keyword + "%", "%" + keyword + "%",
                "%" + keyword + "%", "%" + keyword + "%"
        );
    }

    @Override
    public List<LegalChannelInfo> searchByAddress(String address) {
        return legalChannelInfoRepository.findByAddressLike("%" + address + "%");
    }

    @Override
    public LegalChannelInfo fillInChannelInfo(String type,String name, String contact, String address, String description) {
        return legalChannelInfoRepository.save(LegalChannelInfo.builder().type(type).name(name)
                .contact(contact).address(address).description(description).build());
    }


}
