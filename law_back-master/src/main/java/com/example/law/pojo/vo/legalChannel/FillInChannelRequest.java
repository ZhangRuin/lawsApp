package com.example.law.pojo.vo.legalChannel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class FillInChannelRequest {

    @NotNull
    @NotBlank
    private String type;
    @NotNull
    @Size(min = 1, max = 20, message = "名称为1-20字")
    private String name;
    @NotNull
    @Size(min = 1, max = 100, message = "联系方式为1-100字")
    private String contact;
    @NotNull
    @Size(min = 1, max = 50, message = "地址为1-50字")
    private String address;
    @NotNull
    private String description;

}
