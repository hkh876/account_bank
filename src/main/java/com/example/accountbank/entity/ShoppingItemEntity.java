package com.example.accountbank.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "shopping_items_tbl")
@Getter
public class ShoppingItemEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_item_id")
    private Long id;
    private String name;
    private int marketPrice;
    private int martPrice;
    private int coupangPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_id", nullable = false)
    private ShoppingEntity shopping;
}
