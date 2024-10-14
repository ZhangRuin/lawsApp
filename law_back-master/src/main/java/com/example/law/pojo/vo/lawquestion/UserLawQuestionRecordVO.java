package com.example.law.pojo.vo.lawquestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class UserLawQuestionRecordVO {
    private Long id;
    private String question;
    private Boolean isFinished;
    private Date createdAt;
}
