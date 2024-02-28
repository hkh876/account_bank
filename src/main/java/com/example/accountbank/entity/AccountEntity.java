package com.example.accountbank.entity;

import com.example.accountbank.converter.DivisionConverter;
import com.example.accountbank.enums.Division;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "account_tbl")
@Getter
public class AccountEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;
    private String description;
    private int money;
    private LocalDateTime targetDate;

    @Convert(converter = DivisionConverter.class)
    private Division division;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id")
    private TargetEntity target;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    public void update(AccountEntity account) {
        String description = account.getDescription();
        int money = account.getMoney();
        LocalDateTime targetDate = account.getTargetDate();
        TargetEntity target = account.getTarget();
        CategoryEntity category = account.getCategory();

        if (!this.description.equals(description)) {
            this.description = description;
        }

        if (this.money != money) {
            this.money = money;
        }

        if (!this.targetDate.equals(targetDate)) {
            this.targetDate = targetDate;
        }

        if (!this.target.equals(target)) {
            this.target = target;
        }

        if (!this.category.equals(category)) {
            this.category = category;
        }
    }
}
