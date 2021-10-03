package com.epam.esm.dao.rowmapper;

import com.epam.esm.dao.GiftCertificateFields;
import com.epam.esm.entity.GiftCertificate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

    private String ID = "id";
    private String NAME = "name";
    private String DESCRIPTION = "description";
    private String PRICE = "price";
    private String DURATION = "duration";
    private String CREATE_DATE = "create_date";
    private String LUST_UPDATE_DATE = "last_update_date";

    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int i) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(resultSet.getLong(ID));
        certificate.setName(resultSet.getString(NAME));
        certificate.setDescription(resultSet.getString(DESCRIPTION));
        certificate.setPrice(resultSet.getDouble(PRICE));
        certificate.setDuration(resultSet.getInt(DURATION));
        certificate.setCreateDate(resultSet.getTimestamp(CREATE_DATE).toLocalDateTime());
        certificate.setLastUpdateDate(resultSet.getTimestamp(LUST_UPDATE_DATE).toLocalDateTime());
        return certificate;
    }
}
