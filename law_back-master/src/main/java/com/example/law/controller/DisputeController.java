package com.example.law.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.example.law.pojo.vo.common.ResultResponse;
import com.example.law.pojo.vo.dispute.DisputeHumanAddDataRequest;
import com.example.law.pojo.vo.dispute.DisputeHumanRecordRequest;
import com.example.law.pojo.vo.dispute.DisputeQuestionHumanAskAnswerRequest;
import com.example.law.pojo.vo.dispute.DisputeQuestionHumanRequest;
import com.example.law.pojo.vo.lawquestion.Question;
import com.example.law.service.DisputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/law/dispute")
@Validated
public class DisputeController {

    @Autowired
    private DisputeService disputeService;

    @PostMapping
    public ResultResponse askDisputeQuestion(@Valid @RequestBody Question question) {
        return ResultResponse.success(disputeService.askDisputeQuestion(question.getQuestion(),
                (String) StpUtil.getLoginId()));
    }

    @PostMapping("/human")
    public ResultResponse humanService(@Valid @RequestBody DisputeQuestionHumanRequest disputeQuestionHumanRequest) {
        disputeService.humanService(disputeQuestionHumanRequest.getStaffId(),
                (String) StpUtil.getLoginId(), disputeQuestionHumanRequest.getUserLawQuestionRecordId());
        return ResultResponse.success();
    }

    @PostMapping("/human/ask")
    public ResultResponse humanServiceAsk(@Valid @RequestBody DisputeQuestionHumanAskAnswerRequest request) {
        return ResultResponse.success(disputeService.humanAsk(request.getContent(),
                request.getUserLawQuestionRecordId()));
    }

    @SaCheckRole(value = {"staff"})
    @PostMapping("/human/answer")
    public ResultResponse humanServiceAnswer(@Valid @RequestBody DisputeQuestionHumanAskAnswerRequest request) {
        return ResultResponse.success(disputeService.humanAnswer(request.getContent(),
                request.getUserLawQuestionRecordId()));
    }

    @GetMapping("/human/chat")
    public ResultResponse getHumanChat(@Valid DisputeHumanRecordRequest request) {
        return ResultResponse.success(disputeService.getHumanChat(request.getTimestamp()
                , request.getUserLawQuestionRecordId()));
    }

    @SaCheckRole(value = {"staff"})
    @GetMapping("/human/notfinish")
    public ResultResponse getNotFinishHumanRecord() {
        return ResultResponse.success(disputeService.getNotFinishHumanRecord((String) StpUtil.getLoginId()));
    }

    @SaCheckRole(value = {"staff"})
    @GetMapping("/human/finish")
    public ResultResponse finishHumanRecord(@RequestParam("recordId") Long userDisputeQuestionRecordId) {
        disputeService.finishHumanRecord(userDisputeQuestionRecordId);
        return ResultResponse.success();
    }

    @GetMapping("/record")
    public ResultResponse getRecord() {
        return ResultResponse.success(disputeService.getRecord((String) StpUtil.getLoginId()));
    }

    @SaCheckRole(value = {"staff"})
    @PostMapping("/human/data")
    public ResultResponse humanAddData(@Valid @RequestBody DisputeHumanAddDataRequest request) {
        disputeService.humanAddData(request.getAiTitle(), request.getQuestion(), request.getAnswer());
        return ResultResponse.success();
    }
}
