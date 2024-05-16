package com.example.accountbank.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ShoppingJsonDTO {
    @JsonProperty("shopping_id")
    private Long shoppingId;

    @JsonProperty("register_items")
    private List<ShoppingItemJsonDTO> registerItems;

    @JsonProperty("update_items")
    private List<ShoppingItemJsonDTO> updateItems;

    @JsonProperty("delete_ids")
    private List<Long> deleteIds;
}
