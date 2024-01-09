package com.example.accountbank.repository;

import com.example.accountbank.entity.BudgetEntity;
import com.example.accountbank.entity.CategoryEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class BudgetRepositoryTest {
    @Autowired
    private BudgetRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("예산 등록 테스트")
    public void registerTest() {
        // Given
        CategoryEntity category = categoryRepository.findById(1L).orElseThrow();
        BudgetEntity budget = BudgetEntity.builder()
                .money(10000)
                .category(category)
                .build();

        // When
        BudgetEntity result = repository.save(budget);

        // Then
        Assertions.assertEquals(result.getMoney(), budget.getMoney());
    }

    @Test
    @DisplayName("예산 등록 카테고리 중복 테스트")
    public void findByCategoryTest() {
        // Given
        CategoryEntity category = CategoryEntity.builder()
                .id(1L)
                .build();

        // When
        Optional<BudgetEntity> result = repository.findByCategory(category);

        // Then
        Assertions.assertEquals(result.isPresent(), true);
    }
}
