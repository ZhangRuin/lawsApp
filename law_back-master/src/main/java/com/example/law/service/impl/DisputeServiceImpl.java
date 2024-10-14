package com.example.law.service.impl;

import com.example.law.component.AIServiceMock;
import com.example.law.dao.*;
import com.example.law.pojo.entity.*;
import com.example.law.pojo.vo.common.BizException;
import com.example.law.pojo.vo.common.ExceptionEnum;
import com.example.law.pojo.vo.dispute.DisputeChatMessageVO;
import com.example.law.pojo.vo.dispute.DisputeNotFinishHumanRecordVO;
import com.example.law.pojo.vo.dispute.DisputeQuestionVO;
import com.example.law.pojo.vo.dispute.UserDisputeQuestionRecordVO;
import com.example.law.service.DisputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DisputeServiceImpl implements DisputeService {
    @Autowired
    private AIServiceMock aiService;
    @Autowired
    private DisputeQuestionRepository disputeQuestionRepository;
    @Autowired
    private UserDisputeQuestionRecordRepository userDisputeQuestionRecordRepository;
    @Autowired
    private AIAnswerDisputeQuestionRepository aiAnswerDisputeQuestionRepository;
    @Autowired
    private DisputeChatRepository disputeChatRepository;
    @Autowired
    private DisputeChatMessageRepository disputeChatMessageRepository;

    @Override
    public DisputeQuestionVO askDisputeQuestion(String question, String userId) {
        // 给ai分类的那一项提问次数加一
        List<DisputeQuestion> aiAnswers = aiService.askDisputeQuestionForUser(question);
        ArrayList<DisputeQuestion> res = new ArrayList<>();
        boolean first = true;
        for (DisputeQuestion aiAnswer : aiAnswers) {
            DisputeQuestion temp = disputeQuestionRepository.findByAiTitle(aiAnswer.getAiTitle());
            if (temp != null) {
                if (first) {
                    temp.setQuestionNum(temp.getQuestionNum() + 1);
                    disputeQuestionRepository.save(temp);
                    first = false;
                }
                res.add(temp);
            }
        }
        UserDisputeQuestionRecord userDisputeQuestionRecord = userDisputeQuestionRecordRepository.save(
                UserDisputeQuestionRecord.builder().question(question).userId(userId).build());
        for (DisputeQuestion disputeQuestion : res) {
            aiAnswerDisputeQuestionRepository.save(AIAnswerDisputeQuestion.builder().
                    userLawQuestionRecordId(userDisputeQuestionRecord.getId())
                    .LawQuestionId(disputeQuestion.getId()).build());
        }
        return DisputeQuestionVO.builder().disputeQuestions(res).userDisputeQuestionRecordId(userDisputeQuestionRecord.getId()).build();
    }

    @Override
    public void humanService(String staffId, String userId, Long userDisputeQuestionRecordId) {
        disputeChatRepository.save(DisputeChat.builder().userLawQuestionRecordId(userDisputeQuestionRecordId)
                .userId(userId).staffId(staffId).isFinished(false).build());
    }

    @Override
    public Long humanAsk(String content, Long userDisputeQuestionRecordId) {
        if (disputeChatRepository.findById(userDisputeQuestionRecordId).isPresent()) {
            DisputeChat disputeChat = disputeChatRepository.findById(userDisputeQuestionRecordId).get();
            DisputeChatMessage disputeChatMessage = disputeChatMessageRepository.save(DisputeChatMessage.builder()
                    .lawQuestionChatId(disputeChat.getUserLawQuestionRecordId())
                    .fromId(disputeChat.getUserId())
                    .toId(disputeChat.getStaffId()).content(content).build());
            return disputeChatMessage.getCreatedAt().getTime();
        } else {
            throw new BizException(ExceptionEnum.LAW_QUESTION_RECORD_NOT_FOUND);
        }
    }

    @Override
    public Long humanAnswer(String content, Long userDisputeQuestionRecordId) {
        if (disputeChatRepository.findById(userDisputeQuestionRecordId).isPresent()) {
            DisputeChat disputeChat = disputeChatRepository.findById(userDisputeQuestionRecordId).get();
            DisputeChatMessage disputeChatChatMessage = disputeChatMessageRepository.save(DisputeChatMessage.builder()
                    .lawQuestionChatId(disputeChat.getUserLawQuestionRecordId())
                    .fromId(disputeChat.getStaffId())
                    .toId(disputeChat.getUserId()).content(content).build());
            return disputeChatChatMessage.getCreatedAt().getTime();
        } else {
            throw new BizException(ExceptionEnum.LAW_QUESTION_RECORD_NOT_FOUND);
        }
    }

    @Override
    public DisputeChatMessageVO getHumanChat(Long timestamp, Long userDisputeQuestionRecordId) {
        List<DisputeChatMessage> messages = disputeChatMessageRepository.findByLawQuestionChatIdAndCreatedAtGreaterThan(
                userDisputeQuestionRecordId, new Date(timestamp));
        DisputeChat disputeChat = disputeChatRepository.findById(userDisputeQuestionRecordId).get();
        return DisputeChatMessageVO.builder().chatMessages(messages).isFinished(disputeChat.getIsFinished()).build();
    }

    @Override
    public List<DisputeNotFinishHumanRecordVO> getNotFinishHumanRecord(String staffId) {
        List<DisputeChat> disputeChats = disputeChatRepository.findByStaffIdAndIsFinished(staffId, false);
        ArrayList<DisputeNotFinishHumanRecordVO> res = new ArrayList<>();
        for (DisputeChat disputeChat : disputeChats) {
            UserDisputeQuestionRecord userDisputeQuestionRecord = userDisputeQuestionRecordRepository.
                    findById(disputeChat.getUserLawQuestionRecordId()).get();
            String staffAIAnswer = disputeChat.getStaffAIAnswer();
            if (staffAIAnswer == null) {
                staffAIAnswer = aiService.askDisputeQuestionForStaff(userDisputeQuestionRecord.getQuestion());
                disputeChat.setStaffAIAnswer(staffAIAnswer);
                disputeChatRepository.save(disputeChat);
            }
            res.add(DisputeNotFinishHumanRecordVO.builder().userLawQuestionRecordId(userDisputeQuestionRecord.getId())
                    .question(userDisputeQuestionRecord.getQuestion())
                    .aiAnswer(staffAIAnswer).build());
        }
        return res;
    }

    @Override
    public void finishHumanRecord(Long userDisputeQuestionRecordId) {
        if (disputeChatRepository.findById(userDisputeQuestionRecordId).isPresent()) {
            DisputeChat disputeChat = disputeChatRepository.findById(userDisputeQuestionRecordId).get();
            disputeChat.setIsFinished(true);
            disputeChatRepository.save(disputeChat);
        } else {
            throw new BizException(ExceptionEnum.LAW_QUESTION_RECORD_NOT_FOUND);
        }
    }

    @Override
    public List<UserDisputeQuestionRecordVO> getRecord(String userId) {
        List<UserDisputeQuestionRecord> userDisputeQuestionRecords = userDisputeQuestionRecordRepository.findByUserId(userId);
        ArrayList<UserDisputeQuestionRecordVO> res = new ArrayList<>();
        for (UserDisputeQuestionRecord userDisputeQuestionRecord : userDisputeQuestionRecords) {
            if (disputeChatRepository.findById(userDisputeQuestionRecord.getId()).isPresent()) {
                DisputeChat disputeChat = disputeChatRepository.findById(userDisputeQuestionRecord.getId()).get();
                res.add(UserDisputeQuestionRecordVO.builder().id(userDisputeQuestionRecord.getId())
                        .question(userDisputeQuestionRecord.getQuestion()).
                        createdAt(userDisputeQuestionRecord.getCreatedAt()).isFinished(disputeChat.getIsFinished()).build());
            } else {
                res.add(UserDisputeQuestionRecordVO.builder().id(userDisputeQuestionRecord.getId())
                        .question(userDisputeQuestionRecord.getQuestion()).
                        createdAt(userDisputeQuestionRecord.getCreatedAt()).build());
            }
        }
        return res;
    }

    @Override
    public void humanAddData(String aiTitle, String question, String answer) {
        disputeQuestionRepository.save(DisputeQuestion.builder().aiTitle(aiTitle).question(question)
                .answer(answer).questionNum(1).build());
    }
}
