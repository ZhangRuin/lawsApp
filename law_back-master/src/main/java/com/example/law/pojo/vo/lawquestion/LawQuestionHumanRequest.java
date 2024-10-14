package com.example.law.pojo.vo.lawquestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class LawQuestionHumanRequest {
    @NotBlank(message = "staffId 不能为空")
    private String staffId;
    @NotNull(message = "userLawQuestionRecordId 不能为空")
    private Long userLawQuestionRecordId;
}
