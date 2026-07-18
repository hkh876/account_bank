package com.example.accountbank.repository;

import com.example.accountbank.entity.AccountEntity;
import com.example.accountbank.entity.CategoryEntity;
import com.example.accountbank.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    List<AccountEntity> findAllByTargetDateBetween(LocalDateTime start, LocalDateTime end);
    List<AccountEntity> findAllByMemberAndTargetDateBetween(MemberEntity member, LocalDateTime start, LocalDateTime end);
    List<AccountEntity> findAllByCategoryAndTargetDateBetween(CategoryEntity category, LocalDateTime start, LocalDateTime end);
    List<AccountEntity> findAllByMemberAndCategoryAndTargetDateBetweenOrderByTargetDate(MemberEntity member, CategoryEntity category,
                                                                                        LocalDateTime start, LocalDateTime end);
    List<AccountEntity> findByDescriptionContainingIgnoreCaseOrderByTargetDateDesc(String keyword);
}
