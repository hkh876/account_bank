package com.example.accountbank.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "category_tbl")
@Getter
public class CategoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    private String name;
    private String categoryIcon;
}
