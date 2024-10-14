package com.example.law.dao;

import com.example.law.pojo.entity.UserDisputeQuestionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDisputeQuestionRecordRepository extends JpaRepository<UserDisputeQuestionRecord, Long> {
    List<UserDisputeQuestionRecord> findByUserId(String userId);
}
