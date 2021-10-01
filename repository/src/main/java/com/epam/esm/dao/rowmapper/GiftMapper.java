package com.epam.esm.dao.rowmapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.dao.GiftCertificateColumnName.*;

@Component
@Builder
@AllArgsConstructor
public class GiftMapper implements ResultSetExtractor<List<GiftCertificate>> {

    private GiftCertificateMapper giftCertificateMapper;
    private TagMapper tagMapper;

    @Builder
    @Override

    // Todo transfer fields here !!!
    public List<GiftCertificate> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, GiftCertificate> giftCertificates = new HashMap<>();
        while (resultSet.next()) {

            Long id = resultSet.getLong(ID);
//            String  name = resultSet.getString(NAME);
//            String description = resultSet.getString(DESCRIPTION);
//            Double price =  resultSet.getDouble(PRICE);
//            Integer duration = resultSet.getInt(DURATION);
//            LocalDateTime createDate =  (resultSet.getTimestamp(CREATE_DATE)).toLocalDateTime();
//            LocalDateTime update_date = (resultSet.getTimestamp(LUST_UPDATE_DATE)).toLocalDateTime();
            GiftCertificate giftCertificate = giftCertificates.get(id);
            if (giftCertificate == null) {
                giftCertificate = giftCertificateMapper.mapRow(resultSet, 1); // Todo hard code for number. is it ok ?
//                giftCertificate = new GiftCertificate();
//                giftCertificate.setId(id);
//                giftCertificate.setName(name);
//                giftCertificate.setDescription(description);
//                giftCertificate.setPrice(price);
//                giftCertificate.setDuration(duration);
//                giftCertificate.setCreateDate(createDate);
//                giftCertificate.setLastUpdateDate(update_date);
            }

//            Tag tag = new Tag();
//            Long tagId = resultSet.getLong(TAG_ID);
//            String tagName = resultSet.getString(TAG_NAME);
//            tag.setId(tagId);
//            tag.setName(tagName);
            Tag tag = tagMapper.mapRow(resultSet, 8);
            giftCertificate.addTag(tag);
            giftCertificates.put(giftCertificate.getId(), giftCertificate);
        }
        return giftCertificates.values().stream().toList();
    }
}
