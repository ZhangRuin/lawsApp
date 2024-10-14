package com.example.law.pojo.vo.lawquestion;

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
public class NotFinishHumanRecordVO {
    private Long userLawQuestionRecordId;
    private String question;
    private String aiAnswer;
}
