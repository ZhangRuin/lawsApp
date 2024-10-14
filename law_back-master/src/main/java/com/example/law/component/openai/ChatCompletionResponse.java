package com.example.law.component.openai;

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
public class ChatCompletionResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
    private String system_fingerprint;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
class Choice {
    private Integer index;
    private Message message;
    private String finish_reason;

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
class Message {
    private String role;
    private String content;

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
class Usage {
    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;

}
