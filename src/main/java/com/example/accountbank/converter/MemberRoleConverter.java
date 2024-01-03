package com.example.accountbank.converter;

import com.example.accountbank.enums.MemberRole;
import jakarta.persistence.AttributeConverter;

public class MemberRoleConverter implements AttributeConverter<MemberRole, String> {
    @Override
    public String convertToDatabaseColumn(MemberRole attribute) {
        return attribute.getRole();
    }

    @Override
    public MemberRole convertToEntityAttribute(String dbData) {
        return MemberRole.ofRole(dbData);
    }
}
