package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.DtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    private final static long TEST_ID = 1;
    private final static long TEST_TAG_ID = 2;
    private final static String TEST_TAG_NAME = "Test tag";
    private final static String TEST_NAME = "Cert";
    private final static String TEST_DESCRIPTION = "certificate";
    private final static String TEST_NEW_NAME = "New name";
    private final static String TEST_NEW_DESCRIPTION = "New description";
    private final static double TEST_PRICE = 15.12;
    private final static int TEST_DURATION = 10;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateDao giftCertificateDao;

    @Mock
    private TagDao tagDao;


    private Tag tag;
    private GiftCertificate giftCertificate;
    private TagDto tagDto;
    private GiftCertificateDto validGiftCertificateDto;
    private List<Tag> tags;

    private List<Tag> giftTagList;
    private List<GiftCertificate> giftCertificateList;

    @BeforeEach
    void prepare() {
        tag = new Tag();
        tag.setId(TEST_TAG_ID);
        tag.setName(TEST_TAG_NAME);
        tags = new ArrayList<>();
        tags.add(tag);
        giftCertificate = new GiftCertificate();
        giftCertificate.setId(TEST_ID);
        giftCertificate.setName(TEST_NAME);
        giftCertificate.setDescription(TEST_DESCRIPTION);
        giftCertificate.setPrice(TEST_PRICE);
        giftCertificate.setDuration(TEST_DURATION);
        giftCertificate.setTags(tags);
    }

    @Test
    void getAll() {

    }

    @Test
    void getById() {
        given(giftCertificateDao.findById(TEST_ID)).willReturn(Optional.of(giftCertificate));
        GiftCertificateDto actualGiftCertificateDto = giftCertificateService.getById(TEST_ID);

        GiftCertificateDto expectedCertificateDto = DtoMapper.certificateToDto(giftCertificate);
        Assertions.assertEquals(actualGiftCertificateDto, expectedCertificateDto);
    }

    @Test
    void create() {
        given(giftCertificateDao.create(any())).willReturn(giftCertificate);

//        GiftCertificateDto actualGiftCertificateDto = giftCertificateService.create(TEST_ID);
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void findByAttributes() {
    }

    @Test
    void builder() {
    }
}