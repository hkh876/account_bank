package com.example.accountbank.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BudgetDTO {
    private Long id;
    private int money;
    private CategoryDTO category;

    // Custom
    private Long categoryId;
    private boolean duplicate;
}
