package com.example.accountbank.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ShoppingDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<ShoppingItemDTO> shoppingItems;
}
