package com.example.accountbank.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "budget_tbl")
@Getter
@NoArgsConstructor
public class BudgetEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long id;
    private int money;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    public void update(BudgetEntity budget) {
        int money = budget.getMoney();

        if (this.money != money) {
            this.money = money;
        }
    }

    // For test
    @Builder
    public BudgetEntity(int money, CategoryEntity category) {
        this.money = money;
        this.category = category;
    }
}
