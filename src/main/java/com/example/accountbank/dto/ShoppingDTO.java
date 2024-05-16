package com.example.accountbank.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class ShoppingDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<ShoppingItemDTO> shoppingItems;
}
