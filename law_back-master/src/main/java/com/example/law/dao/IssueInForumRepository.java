package com.example.law.dao;

import com.example.law.pojo.entity.IssueInForum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueInForumRepository extends JpaRepository<IssueInForum, Long> {

    List<IssueInForum> findByIssueTitleOrIssueContentOrIssueUsage(String issueTitle, String issueContent, String issueUsage);

    IssueInForum findByIssueId(Long issueId);
}
