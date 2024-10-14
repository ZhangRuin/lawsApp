package com.example.law.service;

import com.example.law.pojo.entity.IssueInForum;
import com.example.law.pojo.entity.User;
import com.example.law.service.impl.IssueServiceImpl;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IssueService {
    IssueInForum createIssue(String userId,String issueTitle,String issueContent,String issueUsage);

    IssueInForum editMyIssue(String userId,Long issueId,String issueTitle,String issueContent,String issueUsage);

    User createNickname(String userId,String nickName);

    List<IssueInForum> getAllIssues();

    void deleteIssue(Long issueId);

//    User uploadHeadshot(String userId, MultipartFile file);

    User editNickname(String userId,String nickName);
}
