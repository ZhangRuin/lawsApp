package com.example.law.pojo.vo.dispute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class DisputeHumanAddDataRequest {
    @NotBlank(message = "aiTitle 不能为空")
    private String aiTitle;
    @NotBlank(message = "question 不能为空")
    private String question;
    @NotBlank(message = "answer 不能为空")
    private String answer;
}
