package com.example.accountbank.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_tbl")
@Getter
public class ShoppingEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_id")
    private Long id;

    @OneToMany(mappedBy = "shopping", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ShoppingItemEntity> shoppingItems = new ArrayList<>();
}
