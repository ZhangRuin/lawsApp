package com.example.law.dao;

import com.example.law.pojo.entity.LawQuestionChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LawQuestionChatRepository extends JpaRepository<LawQuestionChat, Long> {
    List<LawQuestionChat> findByStaffIdAndIsFinished(String staffId, boolean b);
}
