package com.example.accountbank.service;

import com.example.accountbank.dto.AccountDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@SpringBootTest
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @Autowired
    private DateService dateService;

    @Test
    @DisplayName(value = "날짜에 따른 데이터 조회 서비스 테스트")
    public void findAllByTargetDateBetweenTest() {
        // Given
        String date = "2023-12-12";
        LocalDateTime start = dateService.getStartDateOfMonth(date);
        LocalDateTime end = dateService.getEndDateOfMonth(date);

        // When
        List<AccountDTO> result = accountService.findAllByTargetDateBetween(start, end);

        // Then
        log.info(result);
    }

    @Test
    @DisplayName(value = "같은 카테고리의 금액 합계 구하기 서비스 테스트")
    public void getTotalMoneyBySameCategoryServiceTest() {
        // Given
        String date = "2023-12-12";
        LocalDateTime start = dateService.getStartDateOfMonth(date);
        LocalDateTime end = dateService.getEndDateOfMonth(date);
        List<AccountDTO> accounts = accountService.findAllByTargetDateBetween(start, end);

        // When
        int totalMoney = accountService.getTotalMoneySameCategory(accounts, 1L);

        // Then
        log.info(totalMoney);
    }
}
