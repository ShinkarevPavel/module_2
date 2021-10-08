package com.epam.esm.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.esm.dbconfig.TestConfig;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@Transactional

class GiftCertificateDaoImplTest {

//    @Autowired
//    private final GiftCertificateDaoImpl certificateDao;
//
//    GiftCertificateDaoImplTest(GiftCertificateDaoImpl certificateDao) {
//        this.certificateDao = certificateDao;
//    }


    @Test
    void create() {
    }

    @Test
    void findById() {
//        Optional<GiftCertificate> actual = certificateDao.findById(1L);
//        assertTrue(actual.isPresent());
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void findByCertificateFieldAndSort() {
    }
}