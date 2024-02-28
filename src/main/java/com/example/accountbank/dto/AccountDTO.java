package com.example.accountbank.dto;

import com.example.accountbank.enums.Division;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import static com.example.accountbank.constant.AccountBankConstants.INPUT_DATE_EMPTY_ERROR_MESSAGE;

@Getter
@Setter
@ToString
public class AccountDTO {
    private Long id;
    private String description;
    private int money;
    private LocalDateTime targetDate;
    private TargetDTO target;
    private CategoryDTO category;
    private Division division;

    // Custom
    @NotBlank(message = INPUT_DATE_EMPTY_ERROR_MESSAGE)
    private String dateStr;
    private String displayDate;
    private Long targetId;
    private Long categoryId;
}
