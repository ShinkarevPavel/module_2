package com.epam.esm.dao;

import com.epam.esm.dao.rowmapper.GiftCertificateMapper;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class SqlQueryBuilder {
    private final String MAIN_PART = "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, " +
            "gc.last_update_date, t.id, t.name FROM gift_certificate AS gc LEFT JOIN associativetable " +
            "AS at ON gc.id=at.gift_id LEFT JOIN tags AS t ON at.tag_id=t.id WHERE t.name is null or " +
            "t.name= ? and (gc.name LIKE concat ('%', ?, '%') or gc.description LIKE concat ('%', ?, '%')) ORDER BY ";
    private final String UPDATE_COMMON = "UPDATE gift_certificate SET ";
    private final String WHERE = " WHERE ";
    private final String SPACE = " ";
    private final String COMMA_SIGN = ", ";
    private final String TAG_NAME = "t.name";
//    private final String CERTIFICATE_NAME = "gc.name ";
    private final String CREATE_DATE = "create_date ";
    private final String INJECT_SYMBOLS = "=?";
    private final String AND = " and ";
    private final String DEFAULT_SORT = " ASC";


    private String tagName;
    private String certificateName;
    private String description;
    private String sortedByCreateDate;
    private List<String> values = new ArrayList<>();


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
                sb.append(GiftCertificateMapper.ID);
                sb.append(getINJECT_SYMBOLS());
            }
        }
        return sb.toString();
    }


    public String buildQueryForSearchAndSort(String tagName, String searchPart, List<String> fieldsForSort, List<String> orderSort) {
        StringBuilder sb = new StringBuilder(MAIN_PART);
        values.add(tagName);
        values.add(searchPart);
        values.add(searchPart);
        if (!fieldsForSort.isEmpty() && fieldsForSort != null) {
            if (orderSort.isEmpty() && orderSort == null) {
                sb.append(createSortPartQuery(fieldsForSort, DEFAULT_SORT));
            }

            if (orderSort.size() == 1) {
                sb.append(createSortPartQuery(fieldsForSort,orderSort.get(0)));
            }

            if (orderSort.size() == 2) {
                sb.append(createSortPartQuery(fieldsForSort,orderSort.get(0), orderSort.get(1)));
            }
        } else {
           sb = orderSort.size() > 0 ? sb.append(CREATE_DATE).append(orderSort.get(0))  : sb.append(CREATE_DATE).append(DEFAULT_SORT);
        }
        return sb.toString();
    }

    public String createSortPartQuery(List<String> fieldsForSort, String... sortType) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < fieldsForSort.size(); i++) {
            if (i < fieldsForSort.size()-1) {
                sb.append(fieldsForSort.get(i)).append(SPACE).append(sortType[random.nextInt(sortType.length)]).append(COMMA_SIGN);
            } else {
                sb.append(fieldsForSort.get(i)).append(SPACE).append(sortType[random.nextInt(sortType.length)]);
            }
        }
        return sb.toString();
    }
}
