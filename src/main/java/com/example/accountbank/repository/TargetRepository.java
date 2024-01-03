package com.example.accountbank.repository;

import com.example.accountbank.entity.TargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepository extends JpaRepository<TargetEntity, Long> {
}
