package com.example.law.dao;

import com.example.law.pojo.entity.DisputeChatMessage;
import com.example.law.pojo.entity.LawQuestionChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DisputeChatMessageRepository extends JpaRepository<DisputeChatMessage, Long> {
    List<DisputeChatMessage> findByLawQuestionChatIdAndCreatedAtGreaterThan(Long lawQuestionChatId, Date timestamp);

    List<DisputeChatMessage> findByLawQuestionChatId(Long userLawQuestionRecordId);
}
