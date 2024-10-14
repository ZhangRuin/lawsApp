package com.example.law.service.impl;

import com.example.law.component.AIServiceMock;
import com.example.law.dao.*;
import com.example.law.pojo.entity.*;
import com.example.law.pojo.vo.common.BizException;
import com.example.law.pojo.vo.common.ExceptionEnum;
import com.example.law.pojo.vo.lawquestion.ChatMessageVO;
import com.example.law.pojo.vo.lawquestion.LawQuestionVO;
import com.example.law.pojo.vo.lawquestion.NotFinishHumanRecordVO;
import com.example.law.pojo.vo.lawquestion.UserLawQuestionRecordVO;
import com.example.law.service.AIService;
import com.example.law.service.LawQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LawQuestionServiceImpl implements LawQuestionService {
    @Autowired
    private AIService aiService;
    @Autowired
    private LawQuestionRepository lawQuestionRepository;
    @Autowired
    private UserLawQuestionRecordRepository userLawQuestionRecordRepository;
    @Autowired
    private AIAnswerLawQuestionRepository aiAnswerLawQuestionRepository;
    @Autowired
    private LawQuestionChatRepository lawQuestionChatRepository;
    @Autowired
    private LawQuestionChatMessageRepository lawQuestionChatMessageRepository;

    @Override
    public LawQuestionVO askLawQuestion(String question, String userId) {
        // 给ai分类的那一项提问次数加一
        List<LawQuestion> aiAnswers = aiService.askLawQuestionForUser(question);
        ArrayList<LawQuestion> res = new ArrayList<>();
        boolean first = true;
        for (LawQuestion aiAnswer : aiAnswers) {
            LawQuestion temp = lawQuestionRepository.findByAiTitle(aiAnswer.getAiTitle());
            if (temp != null) {
                if (first) {
                    temp.setQuestionNum(temp.getQuestionNum() + 1);
                    lawQuestionRepository.save(temp);
                    first = false;
                }
                res.add(temp);
            }
        }
        UserLawQuestionRecord userLawQuestionRecord = userLawQuestionRecordRepository.save(
                UserLawQuestionRecord.builder().question(question).userId(userId).build());
        for (LawQuestion lawQuestion : res) {
            aiAnswerLawQuestionRepository.save(AIAnswerLawQuestion.builder().
                    userLawQuestionRecordId(userLawQuestionRecord.getId())
                    .LawQuestionId(lawQuestion.getId()).build());
        }
        return LawQuestionVO.builder().lawQuestions(res).userLawQuestionRecordId(userLawQuestionRecord.getId()).build();
    }

    @Override
    public void humanService(String staffId, String userId, Long userLawQuestionRecordId) {
        lawQuestionChatRepository.save(LawQuestionChat.builder().userLawQuestionRecordId(userLawQuestionRecordId)
                .userId(userId).staffId(staffId).isFinished(false).build());
    }

    @Override
    public Long humanAsk(String content, Long userLawQuestionRecordId) {
        if (lawQuestionChatRepository.findById(userLawQuestionRecordId).isPresent()) {
            LawQuestionChat lawQuestionChat = lawQuestionChatRepository.findById(userLawQuestionRecordId).get();
            LawQuestionChatMessage lawQuestionChatMessage = lawQuestionChatMessageRepository.save(LawQuestionChatMessage.builder()
                    .lawQuestionChatId(lawQuestionChat.getUserLawQuestionRecordId())
                    .fromId(lawQuestionChat.getUserId())
                    .toId(lawQuestionChat.getStaffId()).content(content).build());
            return lawQuestionChatMessage.getCreatedAt().getTime();
        } else {
            throw new BizException(ExceptionEnum.LAW_QUESTION_RECORD_NOT_FOUND);
        }
    }

    @Override
    public Long humanAnswer(String content, Long userLawQuestionRecordId) {
        if (lawQuestionChatRepository.findById(userLawQuestionRecordId).isPresent()) {
            LawQuestionChat lawQuestionChat = lawQuestionChatRepository.findById(userLawQuestionRecordId).get();
            LawQuestionChatMessage lawQuestionChatMessage = lawQuestionChatMessageRepository.save(LawQuestionChatMessage.builder()
                    .lawQuestionChatId(lawQuestionChat.getUserLawQuestionRecordId())
                    .fromId(lawQuestionChat.getStaffId())
                    .toId(lawQuestionChat.getUserId()).content(content).build());
            return lawQuestionChatMessage.getCreatedAt().getTime();
        } else {
            throw new BizException(ExceptionEnum.LAW_QUESTION_RECORD_NOT_FOUND);
        }
    }

    @Override
    public ChatMessageVO getHumanChat(Long timestamp, Long userLawQuestionRecordId) {
        List<LawQuestionChatMessage> messages = lawQuestionChatMessageRepository.findByLawQuestionChatIdAndCreatedAtGreaterThan(
                userLawQuestionRecordId, new Date(timestamp));
        LawQuestionChat lawQuestionChat = lawQuestionChatRepository.findById(userLawQuestionRecordId).get();
        return ChatMessageVO.builder().chatMessages(messages).isFinished(lawQuestionChat.getIsFinished()).build();
    }

    @Override
    public List<NotFinishHumanRecordVO> getNotFinishHumanRecord(String staffId) {
        List<LawQuestionChat> lawQuestionChats = lawQuestionChatRepository.findByStaffIdAndIsFinished(staffId, false);
        ArrayList<NotFinishHumanRecordVO> res = new ArrayList<>();
        for (LawQuestionChat lawQuestionChat : lawQuestionChats) {
            UserLawQuestionRecord userLawQuestionRecord = userLawQuestionRecordRepository.
                    findById(lawQuestionChat.getUserLawQuestionRecordId()).get();
            String staffAIAnswer = lawQuestionChat.getStaffAIAnswer();
            if (staffAIAnswer == null) {
                staffAIAnswer = aiService.askLawQuestionForStaff(userLawQuestionRecord.getQuestion());
                lawQuestionChat.setStaffAIAnswer(staffAIAnswer);
                lawQuestionChatRepository.save(lawQuestionChat);
            }
            res.add(NotFinishHumanRecordVO.builder().userLawQuestionRecordId(userLawQuestionRecord.getId())
                    .question(userLawQuestionRecord.getQuestion())
                    .aiAnswer(staffAIAnswer).build());
        }
        return res;
    }

    @Override
    public void finishHumanRecord(Long userLawQuestionRecordId) {
        if (lawQuestionChatRepository.findById(userLawQuestionRecordId).isPresent()) {
            LawQuestionChat lawQuestionChat = lawQuestionChatRepository.findById(userLawQuestionRecordId).get();
            lawQuestionChat.setIsFinished(true);
            lawQuestionChatRepository.save(lawQuestionChat);
        } else {
            throw new BizException(ExceptionEnum.LAW_QUESTION_RECORD_NOT_FOUND);
        }
    }

    @Override
    public List<UserLawQuestionRecordVO> getRecord(String userId) {
        List<UserLawQuestionRecord> userLawQuestionRecords = userLawQuestionRecordRepository.findByUserId(userId);
        ArrayList<UserLawQuestionRecordVO> res = new ArrayList<>();
        for (UserLawQuestionRecord userLawQuestionRecord : userLawQuestionRecords) {
            if (lawQuestionChatRepository.findById(userLawQuestionRecord.getId()).isPresent()) {
                LawQuestionChat lawQuestionChat = lawQuestionChatRepository.findById(userLawQuestionRecord.getId()).get();
                res.add(UserLawQuestionRecordVO.builder().id(userLawQuestionRecord.getId())
                        .question(userLawQuestionRecord.getQuestion()).
                        createdAt(userLawQuestionRecord.getCreatedAt()).isFinished(lawQuestionChat.getIsFinished()).build());
            } else {
                res.add(UserLawQuestionRecordVO.builder().id(userLawQuestionRecord.getId())
                        .question(userLawQuestionRecord.getQuestion()).
                        createdAt(userLawQuestionRecord.getCreatedAt()).build());
            }
        }
        return res;
    }

    @Override
    public void humanAddData(String aiTitle, String question, String answer) {
        lawQuestionRepository.save(LawQuestion.builder().aiTitle(aiTitle).question(question)
                .answer(answer).questionNum(1).build());
    }
}
