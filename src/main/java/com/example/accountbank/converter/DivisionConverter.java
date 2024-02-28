package com.example.accountbank.converter;

import com.example.accountbank.enums.Division;
import jakarta.persistence.AttributeConverter;

public class DivisionConverter implements AttributeConverter<Division, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Division attribute) {
        return attribute.getCode();
    }

    @Override
    public Division convertToEntityAttribute(Integer dbData) {
        return Division.ofCode(dbData);
    }
}
