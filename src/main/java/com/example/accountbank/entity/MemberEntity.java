package com.example.accountbank.entity;

import com.example.accountbank.converter.MemberRoleConverter;
import com.example.accountbank.enums.MemberRole;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "member_tbl")
@Getter
public class MemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Convert(converter = MemberRoleConverter.class)
    private MemberRole role;

    public void changePassword(String password) {
        if (!this.password.equals(password)) {
            this.password = password;
        }
    }
}
