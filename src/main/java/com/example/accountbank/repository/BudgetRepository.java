package com.example.accountbank.repository;

import com.example.accountbank.entity.BudgetEntity;
import com.example.accountbank.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {
    Optional<BudgetEntity> findByCategory(CategoryEntity category);
}
