package com.example.accountbank.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static com.example.accountbank.constant.AccountBankConstants.NOT_FOUND_DATA_ERROR_MESSAGE;

@Getter
public enum Division {
    DEPOSIT(1, "입금"),
    EXPENSE(2, "지출");

    private int code;
    private String displayName;

    Division(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public static Division ofCode(int code) {
        return Arrays.stream(Division.values())
                .filter(value -> value.getCode() == code)
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_DATA_ERROR_MESSAGE));
    }
}
