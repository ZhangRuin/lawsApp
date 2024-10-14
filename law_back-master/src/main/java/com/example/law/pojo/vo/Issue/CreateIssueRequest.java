package com.example.law.pojo.vo.Issue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class CreateIssueRequest {

    @NotNull
    @Size(min = 1, max = 300, message = "标题为1-300字")
    private String issueTitle;
    @NotNull
    @Size(min = 1, max = 1000, message = "内容为1-1000字")
    private String issueContent;
    @NotNull
    @Size(min = 1, max = 100, message = "用途说明为1-100字")
    private String issueUsage;
    private List<String> issueImages;
}
