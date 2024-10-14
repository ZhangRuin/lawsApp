package com.example.law.service;

import com.example.law.pojo.entity.LawQuestionChatMessage;
import com.example.law.pojo.vo.lawquestion.ChatMessageVO;
import com.example.law.pojo.vo.lawquestion.LawQuestionVO;
import com.example.law.pojo.vo.lawquestion.NotFinishHumanRecordVO;
import com.example.law.pojo.vo.lawquestion.UserLawQuestionRecordVO;

import java.util.List;

public interface LawQuestionService {
    LawQuestionVO askLawQuestion(String question, String userId);

    void humanService(String staffId, String userId, Long userLawQuestionRecordId);

    Long humanAsk(String content, Long userLawQuestionRecordId);

    Long humanAnswer(String content, Long userLawQuestionRecordId);

    ChatMessageVO getHumanChat(Long timestamp, Long userLawQuestionRecordId);

    List<NotFinishHumanRecordVO> getNotFinishHumanRecord(String staffId);

    void finishHumanRecord(Long userLawQuestionRecordId);

    List<UserLawQuestionRecordVO> getRecord(String userId);

    void humanAddData(String aiTitle, String question, String answer);
}
