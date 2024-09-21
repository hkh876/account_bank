package com.example.accountbank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingItemDTO {
    private Long id;
    private String name;
    private int marketPrice;
    private int martPrice;
    private int coupangPrice;
    private ShoppingDTO shopping;
}
