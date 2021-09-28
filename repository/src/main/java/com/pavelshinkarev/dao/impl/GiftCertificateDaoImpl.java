package com.pavelshinkarev.dao.impl;

import com.pavelshinkarev.dao.GiftCertificateDao;
import com.pavelshinkarev.dao.SqlQueryBuilder;
import com.pavelshinkarev.dao.rowmapper.GiftCertificateMapper;
import com.pavelshinkarev.dao.rowmapper.TagMapper;
import com.pavelshinkarev.entity.GiftCertificate;
import com.pavelshinkarev.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static com.pavelshinkarev.dao.SqlQueryBuilder.*;

@Component
@Builder
@AllArgsConstructor

public class GiftCertificateDaoImpl implements GiftCertificateDao {
        // Todo is this good practice ?
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;


    @Override
    public void create(GiftCertificate giftCertificate) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        Optional<GiftCertificate> giftCertificateЕ = Optional.empty();
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection
//                    .prepareStatement("INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, giftCertificate.getName());
//            ps.setString(2, giftCertificate.getDescription());
//            ps.setDouble(3, giftCertificate.getPrice());
//            ps.setInt(4, giftCertificate.getDuration());
//            ps.setString(5, giftCertificate.getCreateDate().toString());
//            ps.setString(6, giftCertificate.getLastUpdateDate().toString());
//            return ps;
//        }, keyHolder);
//        if (keyHolder.getKey() != null) {
//            giftCertificateЕ = findById(keyHolder.getKey().longValue());
//        }

    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.empty();
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<GiftCertificate> findAll() {
        System.out.println(giftCertificateMapper.toString());
        return jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATES, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }
}
