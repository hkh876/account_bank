package com.example.accountbank.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AccountDTO {
    private Long id;
    private String description;
    private int money;
    private LocalDateTime targetDate;
    private TargetDTO target;
    private CategoryDTO category;

    // Custom
    @NotBlank(message = "날짜 정보는 필수 입력 값 입니다.")
    private String dateStr;
    private String displayDate;
    private Long targetId;
    private Long categoryId;
}
