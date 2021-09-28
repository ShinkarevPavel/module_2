package com.pavelshinkarev.dao.rowmapper;

import com.pavelshinkarev.configuration.SpringDataSourceConfiguration;
import com.pavelshinkarev.entity.GiftCertificate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.pavelshinkarev.dao.GiftCertificateColumnName.*;

@Component
@Builder
@AllArgsConstructor
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
        private GiftCertificate certificate;
    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int i) throws SQLException {
        certificate.setId(resultSet.getLong(ID));
        certificate.setName(resultSet.getString(NAME));
        certificate.setDescription(resultSet.getString(DESCRIPTION));
        certificate.setPrice(resultSet.getDouble(PRICE));
        certificate.setDuration(resultSet.getInt(DURATION));
        certificate.setCreateDate((resultSet.getTimestamp(CREATE_DATE)).toLocalDateTime());
        certificate.setLastUpdateDate((resultSet.getTimestamp(LUST_UPDATE_DATE)).toLocalDateTime());
        return certificate;
    }
}
