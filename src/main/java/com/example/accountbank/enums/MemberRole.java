package com.example.accountbank.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static com.example.accountbank.constant.AccountBankConstants.NOT_FOUND_DATA_ERROR_MESSAGE;

@Getter
public enum MemberRole {
    USER(1, "ROLE_USER");

    private int code;
    private String role;

    MemberRole(int code, String role) {
        this.code = code;
        this.role = role;
    }

    public static MemberRole ofRole(String dbData) {
        return Arrays.stream(MemberRole.values())
                .filter(value -> value.getRole().equals(dbData))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_DATA_ERROR_MESSAGE));
    }
}
