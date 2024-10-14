package com.example.law.service;

import com.example.law.pojo.entity.CommentOfIssue;
import com.example.law.pojo.vo.comment.CommentsLikedByUser;

import java.util.List;

public interface CommentService {

    List<CommentOfIssue> getComments(Long issueId);

    CommentOfIssue createComment(String userId, Long issueId, String commentContent);

    void likedComment(Long commentId, String userId);

    void unlikedComment(Long commentId, String userId);

    void deleteCommentByAdmin(Long commentId);

    List<CommentsLikedByUser> commentsLikedByUser(Long issueId, String userId);

}
