package com.example.law.service;

import com.example.law.pojo.entity.LegalDocumentHelp;

import java.util.List;

public interface LegalDocService {
    List<LegalDocumentHelp> legalDocTemplate(String userId, String userAsk);
}
