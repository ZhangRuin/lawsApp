package com.example.law.component;

import com.example.law.pojo.entity.DisputeQuestion;
import com.example.law.pojo.entity.LawQuestion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AIServiceMock {
    public List<LawQuestion> askLawQuestionForUser(String question) {
        //TODO: ai返回最匹配的三个结果，匹配度由高到低，只有aiTitle
        ArrayList<LawQuestion> lawQuestions = new ArrayList<>();
        lawQuestions.add(LawQuestion.builder().aiTitle("录用通知书与劳动合同不一致").build());
        lawQuestions.add(LawQuestion.builder().aiTitle("不符条件解除劳动合同").build());
        return lawQuestions;
    }

    public String askLawQuestionForStaff(String question) {
        //TODO: ai返回给工作人员的回答建议
        return "ai mock";
    }


    public List<DisputeQuestion> askDisputeQuestionForUser(String question) {
        //TODO: ai返回最匹配的三个结果，匹配度由高到低，只有aiTitle
        ArrayList<DisputeQuestion> disputeQuestions = new ArrayList<>();
        disputeQuestions.add(DisputeQuestion.builder().aiTitle("录用通知书与劳动合同不一致").build());
        disputeQuestions.add(DisputeQuestion.builder().aiTitle("不符条件解除劳动合同").build());
        return disputeQuestions;
    }

    public String askDisputeQuestionForStaff(String question) {
        //TODO: ai返回给工作人员的回答建议
        return "ai mock";
    }
}
