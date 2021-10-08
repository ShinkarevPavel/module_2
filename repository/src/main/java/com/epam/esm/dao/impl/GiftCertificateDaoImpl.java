package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.SqlQueryBuilder;
import com.epam.esm.dao.rowmapper.GiftCertificateMapper;
import com.epam.esm.dao.rowmapper.GiftMapper;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String SELECT_ALL_GIFT_CERTIFICATES = "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, " +
            "gc.last_update_date,t.id as t_id, t.name as t_name FROM gift_certificate AS gc LEFT JOIN tag_certificate_associate " +
            "AS at ON gc.id=at.gift_id LEFT JOIN tags AS t ON at.tag_id=t.id";

    private static final String CREATE_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = SELECT_ALL_GIFT_CERTIFICATES + " WHERE gc.id=?";
    private static final String DELETE_BY_ID = "DELETE FROM gift_certificate WHERE id=?";


    private JdbcTemplate jdbcTemplate;
    private GiftCertificateMapper giftCertificateMapper;
    private GiftMapper giftMapper;

    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper, GiftMapper giftMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
        this.giftMapper = giftMapper;
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(CREATE_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, certificate.getName());
            statement.setString(2, certificate.getDescription());
            statement.setDouble(3, certificate.getPrice());
            statement.setInt(4, certificate.getDuration());
            statement.setTimestamp(5, Timestamp.valueOf(certificate.getCreateDate()));
            statement.setTimestamp(6, Timestamp.valueOf(certificate.getLastUpdateDate()));
            return statement;
        }, keyHolder);
        certificate.setId(keyHolder.getKey().longValue());
        return certificate;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return jdbcTemplate.query(SELECT_BY_ID, giftMapper, id).stream().findAny();
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public void update(long id, Map<String, Object> notNullFields) {
        SqlQueryBuilder sqlQueryBuilder = new SqlQueryBuilder();
        String updateQuery = sqlQueryBuilder.buildQueryForUpdate(notNullFields);
        List<Object> value = new ArrayList<>(notNullFields.values());
        value.add(id);
        jdbcTemplate.update(updateQuery, value.toArray());
    }

    @Override
    public List<GiftCertificate> findByCertificateFieldAndSort(String tagName, String searchPart, List<String> fieldsForSort, List<String> orderSort) {
        SqlQueryBuilder sqlQueryBuilder = new SqlQueryBuilder();
        return jdbcTemplate.query(sqlQueryBuilder.buildQueryForSearchAndSort(tagName, searchPart, fieldsForSort, orderSort), giftMapper);
    }
}
