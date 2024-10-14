package com.example.law.dao;

import com.example.law.pojo.entity.LawQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LawQuestionRepository extends JpaRepository<LawQuestion, Long> {
    LawQuestion findByAiTitle(String aiTitle);
}
