package com.epam.esm.entity;

import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        if (Objects.isNull(role)) {
            return null;
        }
        return role.getCode();
    }

    @SneakyThrows
    @Override
    public Role convertToEntityAttribute(String code) {
        if (Strings.isEmpty(code)) {
            return null;
        }
        return Stream.of(Role.values())
                .filter(v -> v.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalAccessException::new);
    }
}
