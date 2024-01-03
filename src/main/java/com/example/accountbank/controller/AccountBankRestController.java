package com.example.accountbank.controller;

import com.example.accountbank.dto.AccountDTO;
import com.example.accountbank.service.AccountService;
import com.example.accountbank.service.DateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(value = "/api/v1/account")
public class AccountBankRestController {
    private final AccountService accountService;
    private final DateService dateService;

    @Autowired
    public AccountBankRestController(AccountService accountService, DateService dateService) {
        this.accountService = accountService;
        this.dateService = dateService;
    }

    @GetMapping("")
    public ResponseEntity<List<AccountDTO>> getAllAccount(String date) {
        // 시작, 끝 날짜 구하기
        LocalDateTime start = dateService.getStartDateOfYear(date);
        LocalDateTime end = dateService.getEndDateOfYear(date);
        List<AccountDTO> accounts = accountService.findAllByTargetDateBetween(start, end);

        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }
}
