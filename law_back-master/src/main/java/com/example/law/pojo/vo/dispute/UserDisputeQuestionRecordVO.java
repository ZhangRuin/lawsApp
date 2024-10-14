package com.example.law.pojo.vo.dispute;

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
public class UserDisputeQuestionRecordVO {
    private Long id;
    private String question;
    private Boolean isFinished;
    private Date createdAt;
}
