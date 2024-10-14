package com.example.law.pojo.vo.Issue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class CreateNicknameRequest {
    @NotNull
    @Size(min = 1, max = 20, message = "昵称长度必须在 1-20 之间")
    @Pattern(regexp = "^[\\w\\u4e00-\\u9fa5]*$", message = "昵称只能包含大小写字母、数字、下划线和汉字")
    private String nickName;
}
