package com.example.law.dao;

import com.example.law.pojo.entity.LegalDocumentHelp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LegalDocHelpRepository extends JpaRepository<LegalDocumentHelp, Long> {
    List<LegalDocumentHelp> findByUserId(String userId);
}
