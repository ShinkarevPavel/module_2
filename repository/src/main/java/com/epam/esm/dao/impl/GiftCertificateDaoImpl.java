package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.SqlQueryBuilder;
import com.epam.esm.dao.rowmapper.GiftCertificateMapper;
import com.epam.esm.dao.rowmapper.GiftMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
@Builder
@RequiredArgsConstructor

public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String SELECT_ALL_GIFT_CERTIFICATES = "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, " +
            "gc.last_update_date,t.id, t.name FROM gift_certificate AS gc LEFT JOIN associativetable " +
            "AS at ON gc.id=at.gift_id LEFT JOIN tags AS t ON at.tag_id=t.id";

    private static final String CREATE_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String CREATE_PAR_INTO_COMMON_TABLE = "INSERT INTO associativetable (gift_id, tag_id) VALUES (?, ?)";
    private static final String SELECT_BY_ID = SELECT_ALL_GIFT_CERTIFICATES + " WHERE gc.id=?";
    private static final String DELETE_BY_ID = "DELETE FROM gift_certificate WHERE id=?";
    private static final String DELETE_BY_ID_FROM_COMMON_TABLE = "DELETE FROM associativetable WHERE id=?";
    private static final String COUNT_BY_ID = "SELECT count(*) FROM gift_certificate WHERE id = ?";
    private static final String FIND_PAR_INTO_COMMON_TABLE = "SELECT count(*) FROM associativetable WHERE gift_id = ? and tag_id=?";

    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;
    private final GiftMapper giftMapper;


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
    public Optional<GiftCertificate> findByName(String name) {
//        ToDo
        return null;
    }


    @Override
    public void addToAssociateTable(long id, List<Tag> tags) {
        for (Tag tag : tags) {
            jdbcTemplate.update(con -> {
                PreparedStatement statement = con.prepareStatement(CREATE_PAR_INTO_COMMON_TABLE);
                statement.setLong(1, id);
                statement.setLong(2, tag.getId());
                return statement;
            });
        }
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return jdbcTemplate.query(SELECT_BY_ID, giftMapper, id).stream().findAny();
    }

    @Override
    public boolean isPresent(long id) {
       return jdbcTemplate.queryForObject(COUNT_BY_ID, Long.class, id) > 0;
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public void deleteFromAssociateTable(long id) {
        jdbcTemplate.update(DELETE_BY_ID_FROM_COMMON_TABLE, id);
    }

    @Override
    public boolean checkAssociateTable(Long certificateId, Long tagId) {
        return jdbcTemplate.queryForObject(FIND_PAR_INTO_COMMON_TABLE, Integer.class, certificateId, tagId) > 0;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATES, giftMapper);
    }

    @Override
    public GiftCertificate update(Map<String, Object> notNullFields, long id) {
        SqlQueryBuilder sqlQueryBuilder = new SqlQueryBuilder();
        String updateQuery = sqlQueryBuilder.buildQueryForUpdate(notNullFields);
        List<Object> value = new ArrayList<>(notNullFields.values());
        value.add(id);
        Object[] parameters = value.toArray(new Object[value.size()]);
        jdbcTemplate.update(updateQuery, parameters);
        return findById(id).get();
    }
}
