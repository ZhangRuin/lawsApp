package com.example.law.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder

public class DisputeChat {
    @Id
    private Long userLawQuestionRecordId;
    private String userId;
    private String staffId;
    @Column(length = 5000)
    private String staffAIAnswer;
    private Boolean isFinished;
    @CreationTimestamp
    private Date createdAt;
}

