package com.example.accountbank.repository;

import com.example.accountbank.entity.BudgetEntity;
import com.example.accountbank.entity.CategoryEntity;
import com.example.accountbank.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {
    List<BudgetEntity> findAllByMember(MemberEntity member);
    Optional<BudgetEntity> findByCategory(CategoryEntity category);
    Optional<BudgetEntity> findByCategoryAndMember(CategoryEntity category, MemberEntity member);
}
