package com.example.accountbank.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
    private Long id;
    private String email;
    private String password;
}
