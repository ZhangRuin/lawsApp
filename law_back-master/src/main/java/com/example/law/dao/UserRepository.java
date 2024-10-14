package com.example.law.dao;

import com.example.law.pojo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId);
    User findByNickName(String nickName);
    List<User> findByRole(User.Role role);
}
