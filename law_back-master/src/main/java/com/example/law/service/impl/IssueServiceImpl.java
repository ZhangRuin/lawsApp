package com.example.law.service.impl;

import com.example.law.dao.IssueInForumRepository;
import com.example.law.dao.UserRepository;
import com.example.law.pojo.entity.IssueInForum;
import com.example.law.pojo.entity.User;

import com.example.law.pojo.vo.common.BizException;
import com.example.law.pojo.vo.common.ExceptionEnum;
import com.example.law.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class IssueServiceImpl implements IssueService {
    @Autowired
    private IssueInForumRepository issueInForumRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public IssueInForum createIssue(String userId, String issueTitle, String issueContent,String issueUsage) {
        User user = userRepository.findByUserId(userId);
        return issueInForumRepository.save(IssueInForum.builder().nickName(user.getNickName()).userId(userId).issueTitle(issueTitle)
                .issueContent(issueContent).issueUsage(issueUsage).build());

    }

    @Override
    public IssueInForum editMyIssue(String userId, Long issueId,String issueTitle, String issueContent, String issueUsage) {
        IssueInForum issue = issueInForumRepository.findByIssueId(issueId);
        if(!userId.equals(issue.getUserId())){
            throw new BizException(ExceptionEnum.EDIT_ISSUE_RIGHT_ERROR);
        }
        issue.setIssueTitle(issueTitle).setIssueContent(issueContent).setIssueUsage(issueUsage);
        return issueInForumRepository.save(issue);
    }

    @Override
    public User createNickname(String userId,String nickName) {
        User user = userRepository.findByUserId(userId);
        User user1 = userRepository.findByNickName(nickName);
        if(user1 != null){
            throw new BizException(ExceptionEnum.NICKNAME_EXIST_SAME_ERROR);
        }
        user.setNickName(nickName);
        return userRepository.save(user);
    }

    @Override
    public User editNickname(String userId,String nickName) {
        User user = userRepository.findByUserId(userId);
        user.setNickName(nickName);
        return userRepository.save(user);
    }

    @Override
    public List<IssueInForum> getAllIssues() {
        return issueInForumRepository.findAll();
    }

    @Override
    public void deleteIssue(Long issueId) {
        if(issueInForumRepository.findByIssueId(issueId)!=null){
            issueInForumRepository.deleteById(issueId);
        }else{
            throw new BizException(ExceptionEnum.ISSUE_NOT_EXIST);
        }
    }
}
