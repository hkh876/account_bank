package com.example.accountbank.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category_tbl")
@Getter
@NoArgsConstructor
public class CategoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    private String name;
    private String categoryIcon;

    // For test
    @Builder
    public CategoryEntity(Long id) {
        this.id = id;
    }
}
