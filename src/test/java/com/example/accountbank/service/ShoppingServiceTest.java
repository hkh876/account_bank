package com.example.accountbank.service;

import com.example.accountbank.dto.ShoppingDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Transactional(propagation = Propagation.SUPPORTS)
@SpringBootTest
public class ShoppingServiceTest {
    @Autowired
    private ShoppingService service;

    @Test
    @DisplayName(value = "장보기 목록 조회 서비스 테스트")
    public void findAllTest() {
        // Given

        // When
        List<ShoppingDTO> result = service.findAll();

        // Then
        log.info(result);
    }
}
