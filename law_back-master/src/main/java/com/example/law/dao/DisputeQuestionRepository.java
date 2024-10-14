package com.example.law.dao;

import com.example.law.pojo.entity.DisputeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisputeQuestionRepository extends JpaRepository<DisputeQuestion, Long> {
    DisputeQuestion findByAiTitle(String aiTitle);
}
