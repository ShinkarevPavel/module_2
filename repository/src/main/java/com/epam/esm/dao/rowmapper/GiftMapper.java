package com.epam.esm.dao.rowmapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Builder
@RequiredArgsConstructor
public class GiftMapper implements ResultSetExtractor<List<GiftCertificate>> {
    private final String ID = "id";
    @NonNull
    private GiftCertificateMapper giftCertificateMapper;
    @NonNull
    private TagMapper tagMapper;


    @Override
    public List<GiftCertificate> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, GiftCertificate> giftCertificates = new HashMap<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong(ID);
            GiftCertificate giftCertificate = giftCertificates.get(id);
            if (giftCertificate == null) {
                giftCertificate = giftCertificateMapper.mapRow(resultSet, 1);
            }
            Tag tag = tagMapper.mapRow(resultSet, 8);
            giftCertificate.addTag(tag);
            giftCertificates.put(giftCertificate.getId(), giftCertificate);
        }
        return giftCertificates.values().stream().toList();
    }
}
