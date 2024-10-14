package com.example.law.pojo.vo.dispute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class DisputeNotFinishHumanRecordVO {
    private Long userLawQuestionRecordId;
    private String question;
    private String aiAnswer;
}
