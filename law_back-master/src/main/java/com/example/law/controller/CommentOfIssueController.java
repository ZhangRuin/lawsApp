package com.example.law.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.law.pojo.entity.CommentOfIssue;
import com.example.law.pojo.entity.IssueInForum;
import com.example.law.pojo.vo.Issue.CreateIssueRequest;
import com.example.law.pojo.vo.comment.CreateCommentRequest;
import com.example.law.pojo.vo.common.ResultResponse;
import com.example.law.service.CommentService;
import com.example.law.service.IssueService;
import com.example.law.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comment")
public class CommentOfIssueController {
    @Autowired
    private UserService userService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private CommentService commentService;

    //创建一个评论
    @PostMapping("/create")
    public ResultResponse createComment(@Valid @RequestBody CreateCommentRequest createCommentRequest) {
        CommentOfIssue comment = commentService.createComment((String) StpUtil.getLoginId(), createCommentRequest.getIssueId(), createCommentRequest.getCommentContent());
        return ResultResponse.success(comment);
    }

    //得到某个issue下的所有评论
    @GetMapping("/{issueId}")
    public ResultResponse getComments(@PathVariable("issueId") Long issueId) {
        return ResultResponse.success(commentService.getComments(issueId));
    }

    //点赞某个评论
    @PostMapping("/like/{commentId}")
    public ResultResponse likedComment(@PathVariable("commentId") Long commentId) {
        commentService.likedComment(commentId, (String) StpUtil.getLoginId());
        return ResultResponse.success("点赞成功");
    }

    //取消对某个评论的点赞
    @DeleteMapping("/unlike/{commentId}")
    @Transactional
    public ResultResponse unlikeComment(@PathVariable("commentId") Long commentId) {
        commentService.unlikedComment(commentId, (String) StpUtil.getLoginId());
        return ResultResponse.success("取消点赞成功");
    }

    //获取当前用户对某个issue下的各个comment的点赞情况
    @GetMapping("/whichCommentsLiked/{issueId}")
    public ResultResponse commentsLikedByUser(@PathVariable("issueId") Long issueId) {
        return ResultResponse.success(commentService.commentsLikedByUser(issueId, (String) StpUtil.getLoginId()));
    }

    //后台可删除评论
    @DeleteMapping("/delete/{commentId}")
    @Transactional
    public ResultResponse deleteIssue(@PathVariable("commentId") Long commentId) {
        commentService.deleteCommentByAdmin(commentId);
        return ResultResponse.success("删除成功");
    }
}
