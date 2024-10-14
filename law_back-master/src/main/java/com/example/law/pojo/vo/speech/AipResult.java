package com.example.law.pojo.vo.speech;

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
public class AipResult {
    private String err_no;
    private String err_msg;
    private String corpus_no;
    private String sn;
    private List<String> result;
}
