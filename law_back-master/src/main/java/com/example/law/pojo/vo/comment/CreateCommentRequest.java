package com.example.law.pojo.vo.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class CreateCommentRequest {
    @NotNull
    private Long issueId;
    @NotNull
    @Size(min = 1, max = 500, message = "评论的内容为1-500字")
    private String commentContent;
}
