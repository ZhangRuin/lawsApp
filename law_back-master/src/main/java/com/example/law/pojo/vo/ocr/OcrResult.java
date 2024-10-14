package com.example.law.pojo.vo.ocr;

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
public class OcrResult {
    private String log_id;
    private String words_result_num;
    private String language;
    private String direction;
    private List<Words> words_result;
}
