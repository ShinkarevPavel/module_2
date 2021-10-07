package com.epam.esm.dao;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SqlQueryBuilder {
    private String MAIN_PART = "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, " +
            "gc.last_update_date, t.id, t.name FROM gift_certificate gc LEFT JOIN tag_certificate_associate " +
            "at ON gc.id=at.gift_id LEFT JOIN tags t ON at.tag_id=t.id WHERE t.name " +
            "like ('%%' %s '%%') and (gc.name LIKE concat ('%%', '%s', '%%') or gc.description LIKE concat ('%%', '%s', '%%'))";
    private final String UPDATE_COMMON = "UPDATE gift_certificate SET ";
    private final String WHERE = " WHERE ";
    private final String SPACE = " ";
    private final String COMMA_SIGN = ", ";
    private final String ORDER_BY = " ORDER BY ";
    private final String INJECT_SYMBOLS = "=?";
    private final String DEFAULT_SORT = "ASC";

    public String buildQueryForUpdate(Map<String, Object> notNullFields) {
        StringBuilder sb = new StringBuilder(UPDATE_COMMON);
        int count = 1;
        for (Map.Entry<String, Object> entry : notNullFields.entrySet()) {
            if (count < notNullFields.size()) {
                sb.append(entry.getKey());
                sb.append(INJECT_SYMBOLS);
                sb.append(COMMA_SIGN);
                count++;
            } else {
                sb.append(entry.getKey());
                sb.append(INJECT_SYMBOLS);
                sb.append(WHERE);
                sb.append(EntityFields.ID.getName());
                sb.append(INJECT_SYMBOLS);
            }
        }
        return sb.toString();
    }

    public String buildQueryForSearchAndSort(String tagName, String searchPart, List<String> fieldsForSort, List<String> orderSort) {
        String query = "";
        if (tagName == null) {
            tagName = "";
        }
        if (searchPart == null) {
            searchPart = "";
        }
        query = String.format(MAIN_PART, "'" + tagName + "'", searchPart, searchPart);
        StringBuilder stringBuilder = new StringBuilder(query);
        if (fieldsForSort != null && !fieldsForSort.isEmpty()) {
            stringBuilder.append(ORDER_BY);
            for (int i = 0; i < fieldsForSort.size(); i++) {
                if (orderSort != null && i < orderSort.size()) {
                    stringBuilder.append(fieldsForSort.get(i)).append(SPACE).append(orderSort.get(i));
                } else {
                    stringBuilder.append(fieldsForSort.get(i)).append(SPACE).append(DEFAULT_SORT);
                }
                if (i < fieldsForSort.size() - 1) {
                    stringBuilder.append(COMMA_SIGN);
                }
            }
        }
        return stringBuilder.toString();
    }
}
