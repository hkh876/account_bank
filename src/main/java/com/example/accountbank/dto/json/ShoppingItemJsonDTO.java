package com.example.accountbank.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShoppingItemJsonDTO {
    private Long id;
    private String name;
    @JsonProperty("market_price")
    private int marketPrice;

    @JsonProperty("mart_price")
    private int martPrice;

    @JsonProperty("coupang_price")
    private int coupangPrice;
}
