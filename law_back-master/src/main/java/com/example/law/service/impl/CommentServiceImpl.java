package com.example.law.service.impl;

import com.example.law.dao.CommentOfIssueRepository;
import com.example.law.dao.IssueInForumRepository;
import com.example.law.dao.LikeCommentRepository;
import com.example.law.dao.UserRepository;
import com.example.law.pojo.entity.CommentOfIssue;
import com.example.law.pojo.entity.IssueInForum;
import com.example.law.pojo.entity.LikeComment;
import com.example.law.pojo.entity.User;
import com.example.law.pojo.vo.comment.CommentsLikedByUser;
import com.example.law.pojo.vo.common.BizException;
import com.example.law.pojo.vo.common.ExceptionEnum;
import com.example.law.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private IssueInForumRepository issueInForumRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentOfIssueRepository commentOfIssueRepository;

    @Autowired
    private LikeCommentRepository likeCommentRepository;

    @Override
    public List<CommentOfIssue> getComments(Long issueId) {
        return commentOfIssueRepository.findByIssueId(issueId);
    }

    @Override
    public CommentOfIssue createComment(String userId, Long issueId, String commentContent) {
        User user = userRepository.findByUserId(userId);
        return commentOfIssueRepository.save(CommentOfIssue.builder().issueId(issueId).nickName(user.getNickName()).userId(userId).
                commentContent(commentContent).likedNumber(0).build());
    }

    @Override
    public void likedComment(Long commentId, String userId) {
        if (commentOfIssueRepository.findByCommentId(commentId) != null) {
            CommentOfIssue comment = commentOfIssueRepository.findByCommentId(commentId);
            likeCommentRepository.save(LikeComment.builder().commentId(commentId)
                    .userId(userId).build());
            commentOfIssueRepository.save(comment.setLikedNumber(comment.getLikedNumber() + 1));
        } else {
            throw new BizException(ExceptionEnum.COMMENT_NOT_EXIST);
        }
    }

    @Override
    public void unlikedComment(Long commentId, String userId) {
        if (commentOfIssueRepository.findByCommentId(commentId) != null) {
            CommentOfIssue comment = commentOfIssueRepository.findByCommentId(commentId);
            if (comment.getLikedNumber() > 0) {
                likeCommentRepository.deleteByCommentIdAndUserId(commentId, userId);
                commentOfIssueRepository.save(comment.setLikedNumber(comment.getLikedNumber() - 1));
            } else {
                throw new BizException(ExceptionEnum.COMMENT_LIKED_NUMBER_IS_ZERO);
            }
        } else {
            throw new BizException(ExceptionEnum.COMMENT_NOT_EXIST);
        }
    }


    @Override
    public void deleteCommentByAdmin(Long commentId) {
        if (commentOfIssueRepository.findByCommentId(commentId) != null) {
            commentOfIssueRepository.deleteById(commentId);
        } else {
            throw new BizException(ExceptionEnum.COMMENT_NOT_EXIST);
        }
    }

    @Override
    public List<CommentsLikedByUser> commentsLikedByUser(Long issueId, String userId) {
        List<CommentOfIssue> comments = commentOfIssueRepository.findByIssueId(issueId);
        ArrayList<CommentsLikedByUser> commentLikeList = new ArrayList<>();
        for(CommentOfIssue comment : comments){
            Long commentId = comment.getCommentId();
            Boolean isLiked = likeCommentRepository.findByCommentIdAndUserId(commentId,userId)!=null;
            commentLikeList.add(CommentsLikedByUser.builder().commentId(commentId).isLiked(isLiked).build());
        }
        return commentLikeList;
    }
}
