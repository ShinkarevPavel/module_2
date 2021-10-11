package com.epam.esm.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.esm.dao.EntityFields;
import com.epam.esm.dbconfig.TestConfig;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@ActiveProfiles("dev")
class GiftCertificateDaoImplTest {

    private static final String TAG_NAME_FOR_SEARCH= "IT";
    private static final String PART_OF_SEARCH = "descr";

    private static final long CERTIFICATE_ID = 1l;
    private static final long NEW_CERTIFICATE_ID = 4l;
    private static final String NEW_TAG_NAME = "newName";
    private static final String NEW_TAG_DESCRIPTION = "newDescription";
    private static final double NEW_TAG_PRICE = 22.0;
    private static final int NEW_TAG_DURATION = 5;
    private static final String UPDATED_NAME = "updatedName";
    private static final String UPDATED_DESCRIPTION = "updatedDescription";
    private static final int UPDATED_DURATION = 7;

    private final GiftCertificateDaoImpl certificateDao;
    private static GiftCertificate newGiftCertificate;
    private static Map<String, Object> notNullFields;

    @BeforeAll
    static void prepare() {
        notNullFields = new HashMap<>();
        notNullFields.put(EntityFields.NAME.getName(), UPDATED_NAME);
        notNullFields.put(EntityFields.DESCRIPTION.getName(), UPDATED_DESCRIPTION);
        notNullFields.put(EntityFields.DURATION.getName(), UPDATED_DURATION);

        newGiftCertificate = GiftCertificate.builder()
                .name(NEW_TAG_NAME)
                .description(NEW_TAG_DESCRIPTION)
                .price(NEW_TAG_PRICE)
                .duration(NEW_TAG_DURATION)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }

    @Autowired
    GiftCertificateDaoImplTest(GiftCertificateDaoImpl certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Test
    @Order(1)
    void create() {
        GiftCertificate actual = certificateDao.create(newGiftCertificate);
        assertNotNull(actual.getId());
        assertEquals(actual.getName(), NEW_TAG_NAME);
        assertEquals(actual.getDescription(), NEW_TAG_DESCRIPTION);
        assertEquals(actual.getPrice(), NEW_TAG_PRICE);
        assertEquals(actual.getDuration(), NEW_TAG_DURATION);
    }

    @Test
    void findById() {
        Optional<GiftCertificate> actual = certificateDao.findById(CERTIFICATE_ID);
        assertTrue(actual.isPresent());
    }

    @Test
    void notFindById() {
        Optional<GiftCertificate> actual = certificateDao.findById(NEW_CERTIFICATE_ID);
        assertFalse(actual.isPresent());
    }

    @Test
    @Order(2)
    void delete() {
        certificateDao.delete(NEW_CERTIFICATE_ID);
    }

    @Test
    void findByCertificateFieldAndSortByTagAndPartOFSearch() {
        List<GiftCertificate> actual = certificateDao
                .findByCertificateFieldAndSort(TAG_NAME_FOR_SEARCH, PART_OF_SEARCH, null, null);
    assertEquals(actual.size(), 1);
    }

    @Test
    void findByCertificateFieldAndSortIfAllParametersNull() {
        List<GiftCertificate> actual = certificateDao
                .findByCertificateFieldAndSort(null, null, null, null);
        assertEquals(actual.size(), 2);
    }

}