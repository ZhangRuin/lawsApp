package com.example.law.service;

import com.example.law.pojo.entity.LawQuestion;

import java.util.List;

public interface AIService {
    List<String> askLegalDoc(String conversationContent);

    String askConversationWarning(String conversationContent);

    String askContractWarning(String contrcatContent);

    List<LawQuestion> askLawQuestionForUser(String question);

    String askLawQuestionForStaff(String question);
}
