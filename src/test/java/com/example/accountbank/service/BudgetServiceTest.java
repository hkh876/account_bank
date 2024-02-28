package com.example.accountbank.service;

import com.example.accountbank.dto.BudgetDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Log4j2
@SpringBootTest
public class BudgetServiceTest {
    @Autowired
    private BudgetService service;

    @Test
    @DisplayName(value = "카테고리 별 예산 금액 구하기 서비스 테스트")
    public void getBudgetMoneyByCategoryServiceTest() {
        // Given
        List<BudgetDTO> budgets = service.findAll();
        Long categoryId = 1L;

        // When
        int budgetMoney = service.getMoneyByCategory(budgets, categoryId);

        // Then
        if (categoryId == 1L) {
            // 카테고리가 존재 한다면
            Assertions.assertNotEquals(budgetMoney, 0);
        } else {
            // 카테고리가 존재 하지 않는다면
            Assertions.assertEquals(budgetMoney, 0);
        }

        log.info(budgetMoney);
    }
}
