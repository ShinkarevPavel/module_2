package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String SELECT_ALL_GIFT_CERTIFICATES = "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, " +
            "gc.last_update_date,t.id as t_id, t.name as t_name FROM gift_certificate AS gc LEFT JOIN tag_certificate_associate " +
            "AS at ON gc.id=at.gift_id LEFT JOIN tags AS t ON at.tag_id=t.id";

    private static final String CREATE_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = SELECT_ALL_GIFT_CERTIFICATES + " WHERE gc.id=?";
    private static final String DELETE_BY_ID = "DELETE FROM gift_certificate WHERE id=?";

    private EntityManager entityManager;

    public GiftCertificateDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        entityManager.persist(certificate);
        return certificate;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public void delete(long id) {
        entityManager.remove(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
       entityManager.merge(giftCertificate);
    }


    @Override
    public List<GiftCertificate> findByCertificateFieldAndSort(String tagName, String searchPart, List<String> fieldsForSort, List<String> orderSort) {
//        SqlQueryBuilder sqlQueryBuilder = new SqlQueryBuilder();
//        return jdbcTemplate.query(sqlQueryBuilder.buildQueryForSearchAndSort(tagName, searchPart, fieldsForSort, orderSort), giftMapper);
        return null;
    }
}
