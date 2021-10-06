package com.epam.esm.dao;

import java.util.ArrayList;
import java.util.List;

public enum EntityFields {

    ID("id"),

    NAME("name"),

    DESCRIPTION("description"),

    PRICE("price"),

    DURATION("duration"),

    CREATE_DATE("create_date"),

    LAST_UPDATE_DATE("last_update_date");

    private String name;

    EntityFields(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<String> getFields() {
        List<String> fields = new ArrayList<>();
        EntityFields[] values = EntityFields.values();
        for (EntityFields e : values) {
            fields.add(e.getName());
        }
        return fields;
    }

}
