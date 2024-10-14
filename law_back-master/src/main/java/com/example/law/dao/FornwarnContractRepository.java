package com.example.law.dao;

import com.example.law.pojo.entity.ForewarnContract;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FornwarnContractRepository extends JpaRepository<ForewarnContract, Long> {
    List<ForewarnContract> findByUserId(String userId);
}
