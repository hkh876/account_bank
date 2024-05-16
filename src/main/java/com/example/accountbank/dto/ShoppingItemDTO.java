package com.example.accountbank.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShoppingItemDTO {
    private Long id;
    private String name;
    private int marketPrice;
    private int martPrice;
    private int coupangPrice;
    private ShoppingDTO shopping;
}
