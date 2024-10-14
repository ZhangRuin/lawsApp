package com.example.law.dao;

import com.example.law.pojo.entity.LegalChannelInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LegalChannelInfoRepository extends JpaRepository<LegalChannelInfo, Long> {
    List<LegalChannelInfo> findByType(String type);

    List<LegalChannelInfo> findByAddressLike(String address);

    List<LegalChannelInfo> findByNameLikeOrContactLikeOrAddressLikeOrDescriptionLike(String name, String contact, String address,String description);

}
