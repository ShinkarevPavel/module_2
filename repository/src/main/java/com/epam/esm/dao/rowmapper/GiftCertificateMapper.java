package com.epam.esm.dao.rowmapper;

import com.epam.esm.entity.GiftCertificate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.dao.EntityFields.*;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int i) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(resultSet.getLong(ID.getName()));
        certificate.setName(resultSet.getString(NAME.getName()));
        certificate.setDescription(resultSet.getString(DESCRIPTION.getName()));
        certificate.setPrice(resultSet.getDouble(PRICE.getName()));
        certificate.setDuration(resultSet.getInt(DURATION.getName()));
        certificate.setCreateDate(resultSet.getTimestamp(CREATE_DATE.getName()).toLocalDateTime());
        certificate.setLastUpdateDate(resultSet.getTimestamp(LAST_UPDATE_DATE.getName()).toLocalDateTime());
        return certificate;
    }
}
