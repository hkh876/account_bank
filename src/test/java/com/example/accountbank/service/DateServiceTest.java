package com.example.accountbank.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@Log4j2
@SpringBootTest
public class DateServiceTest {
    @Autowired
    private DateService service;

    @Test
    @DisplayName(value = "입력 날짜 문자열 변환 서비스 테스트")
    public void stringDateToLocalDateTimeServiceTest() {
        // Given
        String input = "2023-12-12";

        // When
        LocalDateTime localDateTime = service.dateStrToLocalDateTime(input);

        // Then
        log.info(localDateTime.toString());
    }
}
