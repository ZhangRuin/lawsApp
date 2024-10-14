package com.example.law.pojo.vo.dispute;

import com.example.law.pojo.entity.DisputeChatMessage;
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
public class DisputeChatMessageVO {
    private List<DisputeChatMessage> chatMessages;
    private Boolean isFinished;
}
