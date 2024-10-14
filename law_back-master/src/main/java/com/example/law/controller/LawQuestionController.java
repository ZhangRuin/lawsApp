package com.example.law.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.example.law.pojo.vo.lawquestion.*;
import com.example.law.pojo.vo.common.ResultResponse;
import com.example.law.service.LawQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/law/question")
@Validated
public class LawQuestionController {
    @Autowired
    private LawQuestionService lawQuestionService;

    @PostMapping
    public ResultResponse askLawQuestion(@Valid @RequestBody Question question) {
        return ResultResponse.success(lawQuestionService.askLawQuestion(question.getQuestion(),
                (String) StpUtil.getLoginId()));
    }

    @PostMapping("/human")
    public ResultResponse humanService(@Valid @RequestBody LawQuestionHumanRequest lawQuestionHumanRequest) {
        lawQuestionService.humanService(lawQuestionHumanRequest.getStaffId(),
                (String) StpUtil.getLoginId(), lawQuestionHumanRequest.getUserLawQuestionRecordId());
        return ResultResponse.success();
    }

    @PostMapping("/human/ask")
    public ResultResponse humanServiceAsk(@Valid @RequestBody LawQuestionHumanAskAnswerRequest request) {
        return ResultResponse.success(lawQuestionService.humanAsk(request.getContent(),
                request.getUserLawQuestionRecordId()));
    }

    @SaCheckRole(value = {"staff"})
    @PostMapping("/human/answer")
    public ResultResponse humanServiceAnswer(@Valid @RequestBody LawQuestionHumanAskAnswerRequest request) {
        return ResultResponse.success(lawQuestionService.humanAnswer(request.getContent(),
                request.getUserLawQuestionRecordId()));
    }

    @GetMapping("/human/chat")
    public ResultResponse getHumanChat(@Valid HumanRecordRequest request) {
        return ResultResponse.success(lawQuestionService.getHumanChat(request.getTimestamp()
                , request.getUserLawQuestionRecordId()));
    }

    @SaCheckRole(value = {"staff"})
    @GetMapping("/human/notfinish")
    public ResultResponse getNotFinishHumanRecord() {
        return ResultResponse.success(lawQuestionService.getNotFinishHumanRecord((String) StpUtil.getLoginId()));
    }

    @SaCheckRole(value = {"staff"})
    @GetMapping("/human/finish")
    public ResultResponse finishHumanRecord(@RequestParam("recordId") Long userLawQuestionRecordId) {
        lawQuestionService.finishHumanRecord(userLawQuestionRecordId);
        return ResultResponse.success();
    }

    @GetMapping("/record")
    public ResultResponse getRecord() {
        return ResultResponse.success(lawQuestionService.getRecord((String) StpUtil.getLoginId()));
    }

    @SaCheckRole(value = {"staff"})
    @PostMapping("/human/data")
    public ResultResponse humanAddData(@Valid @RequestBody HumanAddDataRequest request) {
        lawQuestionService.humanAddData(request.getAiTitle(), request.getQuestion(), request.getAnswer());
        return ResultResponse.success();
    }
}

