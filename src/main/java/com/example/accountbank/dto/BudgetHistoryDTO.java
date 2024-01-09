package com.example.accountbank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetHistoryDTO {
    private CategoryDTO category;
    private int totalMoney;
    private int budgetMoney;
}
