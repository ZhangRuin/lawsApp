package com.example.law.pojo.vo.user;

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
public class AddStaffRequest {
    @NotBlank(message = "用户ID不能为空")
    private String userId;
    @NotBlank(message = "密码不能为空")
    private String staffPassword;
}
