package com.example.accountbank.repository;

import com.example.accountbank.entity.ShoppingItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingItemRepository extends JpaRepository<ShoppingItemEntity, Long> {
}
