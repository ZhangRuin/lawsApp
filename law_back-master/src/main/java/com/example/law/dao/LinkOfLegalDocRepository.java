package com.example.law.dao;

import com.example.law.pojo.entity.LinkOfLegalDoc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkOfLegalDocRepository extends JpaRepository<LinkOfLegalDoc, Long> {
    LinkOfLegalDoc findByCategory(String category);
}
