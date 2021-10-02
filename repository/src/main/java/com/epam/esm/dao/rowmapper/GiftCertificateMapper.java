package com.epam.esm.dao.rowmapper;

import com.epam.esm.dao.GiftCertificateFields;
import com.epam.esm.entity.GiftCertificate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int i) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(resultSet.getLong(GiftCertificateFields.ID));
        certificate.setName(resultSet.getString(GiftCertificateFields.NAME));
        certificate.setDescription(resultSet.getString(GiftCertificateFields.DESCRIPTION));
        certificate.setPrice(resultSet.getDouble(GiftCertificateFields.PRICE));
        certificate.setDuration(resultSet.getInt(GiftCertificateFields.DURATION));
        certificate.setCreateDate(resultSet.getTimestamp(GiftCertificateFields.CREATE_DATE).toLocalDateTime());
        certificate.setLastUpdateDate(resultSet.getTimestamp(GiftCertificateFields.LUST_UPDATE_DATE).toLocalDateTime());
        return certificate;
    }
}
