package com.example.law.dao;

import com.example.law.pojo.entity.CommentOfIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentOfIssueRepository extends JpaRepository<CommentOfIssue,Long> {
    List<CommentOfIssue> findByIssueId(Long issueId);

    CommentOfIssue findByCommentId(Long commentId);

}
