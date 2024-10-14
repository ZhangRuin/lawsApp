package com.example.law.dao;

import com.example.law.pojo.entity.UserLawQuestionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLawQuestionRecordRepository extends JpaRepository<UserLawQuestionRecord, Long> {
    List<UserLawQuestionRecord> findByUserId(String userId);
}
