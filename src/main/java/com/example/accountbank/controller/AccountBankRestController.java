package com.example.accountbank.controller;

import com.example.accountbank.dto.AccountDTO;
import com.example.accountbank.dto.ShoppingDTO;
import com.example.accountbank.dto.json.ShoppingItemJsonDTO;
import com.example.accountbank.dto.json.ShoppingJsonDTO;
import com.example.accountbank.service.AccountService;
import com.example.accountbank.service.DateService;
import com.example.accountbank.service.ShoppingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.accountbank.constant.AccountBankConstants.*;

@Log4j2
@RestController
@RequestMapping(ACCOUNT_BANK_API)
public class AccountBankRestController {
    private final AccountService accountService;
    private final ShoppingService shoppingService;
    private final DateService dateService;

    @Autowired
    public AccountBankRestController(AccountService accountService, ShoppingService shoppingService, DateService dateService) {
        this.accountService = accountService;
        this.shoppingService = shoppingService;
        this.dateService = dateService;
    }

    @GetMapping("")
    public ResponseEntity<List<AccountDTO>> getAllAccount(String date) {
        // 시작, 끝 날짜 구하기
        LocalDateTime start = dateService.getStartDateOfMonth(date);
        LocalDateTime end = dateService.getEndDateOfMonth(date);
        List<AccountDTO> accounts = accountService.findAllByTargetDateBetween(start, end);

        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @DeleteMapping(GROCERY_SHOPPING_DELETE_REST_API)
    public ResponseEntity<String> deleteShopping(@RequestBody ShoppingJsonDTO data) {
        shoppingService.delete(data.getShoppingId());
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    @PostMapping(GROCERY_SHOPPING_ITEM_REGISTER_REST_API)
    public ResponseEntity<ShoppingDTO> registerShoppingItem(@RequestBody List<ShoppingItemJsonDTO> data) {
        ShoppingDTO shopping = shoppingService.itemRegister(data);
        return ResponseEntity.status(HttpStatus.OK).body(shopping);
    }

    @PutMapping(GROCERY_SHOPPING_ITEM_UPDATE_REST_API)
    public ResponseEntity<String> updateShoppingItem(@RequestBody ShoppingJsonDTO data) {
        shoppingService.itemUpdate(data);
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
