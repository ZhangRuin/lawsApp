package com.example.law.pojo.vo.dispute;

import com.example.law.pojo.entity.DisputeQuestion;
import com.example.law.pojo.entity.LawQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class DisputeQuestionVO {
    private Long userDisputeQuestionRecordId;
    private List<DisputeQuestion> disputeQuestions;
}
