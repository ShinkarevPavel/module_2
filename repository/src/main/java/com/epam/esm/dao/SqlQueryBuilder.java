package com.epam.esm.dao;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Builder
@Getter
@NoArgsConstructor
public class SqlQueryBuilder {

    private final String NAME = " name=?";
    private final String DESCRIPTION = " description=?";
    private final String PRICE = " price=?";
    private final String DURATION = " duration=?";
    private final String LAST_UPDATE_DATE = " last_update_date=?";
    private final String COMMA_SIGN = ",";
    private final String UPDATE_COMMON = "UPDATE gift_certificate SET";
    private final String WHERE = " WHERE id=?";


    public String buildQueryForUpdate(Map<String, Object> notNullFields) {
        StringBuilder sb = new StringBuilder(UPDATE_COMMON);
        int count = 1;
        for (Map.Entry<String, Object> entry : notNullFields.entrySet()) {
            if (count < notNullFields.size()) {
                sb.append(entry.getKey());
                sb.append(COMMA_SIGN);
                count++;
            } else {
                sb.append(entry.getKey());
            }
        }
        sb.append(WHERE);
        return sb.toString();
    }
}
