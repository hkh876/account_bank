package com.example.accountbank.service;

import com.example.accountbank.dto.AccountDTO;
import com.example.accountbank.entity.AccountEntity;
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
        LocalDateTime start = dateService.getStartDateOfYear(date);
        LocalDateTime end = dateService.getEndDateOfYear(date);

        // When
        List<AccountDTO> result = accountService.findAllByTargetDateBetween(start, end);

        // Then
        log.info(result);
    }
}
