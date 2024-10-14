package com.example.law.dao;

import com.example.law.pojo.entity.LawQuestionChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface LawQuestionChatMessageRepository extends JpaRepository<LawQuestionChatMessage, Long> {
    List<LawQuestionChatMessage> findByLawQuestionChatIdAndCreatedAtGreaterThan(Long lawQuestionChatId, Date timestamp);

    List<LawQuestionChatMessage> findByLawQuestionChatId(Long userLawQuestionRecordId);
}
