package com.example.law.service;

import com.example.law.pojo.vo.dispute.DisputeChatMessageVO;
import com.example.law.pojo.vo.dispute.DisputeNotFinishHumanRecordVO;
import com.example.law.pojo.vo.dispute.DisputeQuestionVO;
import com.example.law.pojo.vo.dispute.UserDisputeQuestionRecordVO;


import java.util.List;

public interface DisputeService {
    DisputeQuestionVO askDisputeQuestion(String question, String userId);

    void humanService(String staffId, String userId, Long userLawQuestionRecordId);

    Long humanAsk(String content, Long userLawQuestionRecordId);

    Long humanAnswer(String content, Long userLawQuestionRecordId);

    DisputeChatMessageVO getHumanChat(Long timestamp, Long userLawQuestionRecordId);

    List<DisputeNotFinishHumanRecordVO> getNotFinishHumanRecord(String staffId);

    void finishHumanRecord(Long userLawQuestionRecordId);

    List<UserDisputeQuestionRecordVO> getRecord(String userId);

    void humanAddData(String aiTitle, String question, String answer);
}
