package com.example.accountbank.repository;

import com.example.accountbank.entity.AccountEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Log4j2
@SpringBootTest
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository repository;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Test
    @DisplayName(value = "해당 연도 데이터 목록 조회 테스트")
    public void findAllByYearTest() {
        // Given
        String inputYear = "2023-11";
        YearMonth yearMonth = YearMonth.parse(inputYear);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        LocalDateTime start = startDate.atTime(0, 0);
        LocalDateTime end = endDate.atTime(23, 59);

        // When
        List<AccountEntity> result = repository.findAllByTargetDateBetween(start, end);

        // Then
        log.info(result);
    }

    @Test
    @DisplayName(value = "해당 연도 데이터 목록 카테고리 순 조회 테스트")
    @Transactional(readOnly = true)
    public void findAllByTargetDateBetweenOrderByCategoryAscTest() {
        // Given
        String date = "2023-12-01";
        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime startDate = localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime endDate = localDate.with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59);

        // When

        // Then

    }
}
