package com.example.law.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.File;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class User {
    public enum Role {
        ADMIN, STAFF, USER
    }

    @Id
    private String userId;
    private String nickName;//昵称
    private String profileImage;//头像
    private Role role;
}
