package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.rowmapper.GiftCertificateMapper;
import com.epam.esm.dao.rowmapper.GiftMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.dao.sqlqueries.CertificateSqlQuery.*;

@Repository
@Component
@Builder
@AllArgsConstructor
public class GiftCertificateDaoImpl implements GiftCertificateDao {

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
    public void delete(long id) {

    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATES, giftMapper);
    }

    public void update() {

    }
}
