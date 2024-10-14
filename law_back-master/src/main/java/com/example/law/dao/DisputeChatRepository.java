package com.example.law.dao;

import com.example.law.pojo.entity.DisputeChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisputeChatRepository extends JpaRepository<DisputeChat, Long> {
    List<DisputeChat> findByStaffIdAndIsFinished(String staffId, boolean b);
}
