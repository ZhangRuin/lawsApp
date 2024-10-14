package com.example.law.pojo.vo.lawquestion;

import com.example.law.pojo.entity.LawQuestionChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class ChatMessageVO {
    private List<LawQuestionChatMessage> chatMessages;
    private Boolean isFinished;
}
