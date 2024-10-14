package com.example.law.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.law.pojo.entity.IssueInForum;
import com.example.law.pojo.entity.User;
import com.example.law.pojo.vo.Issue.CreateIssueRequest;
import com.example.law.pojo.vo.Issue.CreateNicknameRequest;
import com.example.law.pojo.vo.Issue.EditIssueRequest;
import com.example.law.pojo.vo.common.ResultResponse;
import com.example.law.service.IssueService;
import com.example.law.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/issue")
public class IssueInForumController {
    @Autowired
    private UserService userService;
    @Autowired
    private IssueService issueService;


    //创建一个帖子
    @PostMapping("/create")
    public ResultResponse createIssue(@Valid @RequestBody CreateIssueRequest createIssueRequest){
        IssueInForum issue = issueService.createIssue((String) StpUtil.getLoginId(),createIssueRequest.getIssueTitle(),
                createIssueRequest.getIssueContent(),createIssueRequest.getIssueUsage());
        return ResultResponse.success(issue);
    }

    //编辑自己的帖子
    @PostMapping("/edit")
    public ResultResponse editIssue(@Valid @RequestBody EditIssueRequest editIssueRequest){
        IssueInForum issue = issueService.editMyIssue((String) StpUtil.getLoginId(),editIssueRequest.getIssueId(),
                editIssueRequest.getIssueTitle(), editIssueRequest.getIssueContent(),editIssueRequest.getIssueUsage());
        return ResultResponse.success(issue);
    }

    //返回所有帖子
    @GetMapping("/all")
    public ResultResponse getAllIssues() {
        return ResultResponse.success(issueService.getAllIssues());
    }


    //后台可删除帖子
    @DeleteMapping("/delete/{issueId}")
    @Transactional
    public ResultResponse deleteIssue(@PathVariable("issueId") Long issueId) {
        issueService.deleteIssue(issueId);;
        return ResultResponse.success("删除成功");
    }

}
