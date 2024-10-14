package com.example.law.dao;

import com.example.law.pojo.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    void deleteByCommentIdAndUserId(Long commentId, String userId);

    LikeComment findByCommentIdAndUserId(Long commentId, String userId);
}
