package com.epam.esm.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.esm.dbconfig.TestConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.PageParameter;
import com.epam.esm.entity.SearchParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfig.class)
@Transactional
class GiftCertificateDaoImplTest {

    private static final long CERTIFICATE_ID = 1l;
    private static final long NEW_CERTIFICATE_ID = 4l;
    private static final String NEW_TAG_NAME = "newName";
    private static final String NEW_TAG_DESCRIPTION = "newDescription";
    private static final double NEW_TAG_PRICE = 22.0;
    private static final int NEW_TAG_DURATION = 5;


    private  GiftCertificateDaoImpl certificateDao;


    private GiftCertificate newGiftCertificate;
    private PageParameter pageParameter;
    private SearchParameter searchParameter;


    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDaoImpl certificateDao) {
        this.certificateDao = certificateDao;
    }

    @BeforeEach
    void prepare() {
        newGiftCertificate = GiftCertificate.builder()
                .name(NEW_TAG_NAME)
                .description(NEW_TAG_DESCRIPTION)
                .price(NEW_TAG_PRICE)
                .duration(NEW_TAG_DURATION)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();

        pageParameter = PageParameter.builder()
                .size(2)
                .page(0)
                .build();
        searchParameter = SearchParameter.builder()
                .tagName(List.of("IT"))
                .fieldsForSort(List.of("duration"))
                .orderSort(List.of("desc"))
                .searchPart("")
                .build();

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
    @Order(3)
    void notFindById() {
        Optional<GiftCertificate> actual = certificateDao.findById(NEW_CERTIFICATE_ID);
        assertFalse(actual.isPresent());
    }

    @Test
    @Order(2)
    void delete() {
        certificateDao.delete(1L);
    }

    @Test
    void findByCertificateFieldAndSortByTagAndPartOFSearch() {
        List<GiftCertificate> certificates = certificateDao.findByCertificateFieldAndSort(searchParameter, pageParameter);
        assertEquals(3, certificates.get(0).getDuration());
        assertEquals(2, certificates.size());
    }
}