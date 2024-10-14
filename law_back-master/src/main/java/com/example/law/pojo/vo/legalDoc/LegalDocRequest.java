package com.example.law.pojo.vo.legalDoc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class LegalDocRequest {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 1000, message = "内容字数有误")
    private String ask;
}
