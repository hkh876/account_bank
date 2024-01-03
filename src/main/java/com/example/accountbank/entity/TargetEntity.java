package com.example.accountbank.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "target_tbl")
@Getter
public class TargetEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "target_id")
    private Long id;
    private String name;
    private String targetIcon;
}
