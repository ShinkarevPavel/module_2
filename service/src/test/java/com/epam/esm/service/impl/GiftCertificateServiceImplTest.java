package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.util.DtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GiftCertificateServiceImpl.class)
class GiftCertificateServiceImplTest {

    private static final long TEST_ID = 1;
    private static final long NO_SUCH_CERTIFICATE_ID = 2;
    private static final long TEST_TAG_ID = 2;
    private static final long NOT_VALID_TAG_ID = 2052;
    private static final String TEST_TAG_NAME = "Test tag";
    private static final String TEST_NAME = "CertificateOne";
    private static final String TEST_DESCRIPTION = "certificate";
    private static final double TEST_PRICE = 15.12;
    private static final int TEST_DURATION = 10;

    @Autowired
    private GiftCertificateServiceImpl giftCertificateService;

    @MockBean
    private GiftCertificateDao giftCertificateDao;

    @MockBean
    private TagDao tagDao;

    private Tag tag;
    private GiftCertificate giftCertificate;
    private TagDto tagDto;
    private GiftCertificateDto validGiftCertificateDto;
    private Set<Tag> tags;
    private Set<TagDto> dtoTags;
    private List<GiftCertificate> giftCertificates;


    @BeforeEach
    void prepare() {
        tag = new Tag();
        tag.setId(TEST_TAG_ID);
        tag.setName(TEST_TAG_NAME);

        tags = new HashSet<>();
        tags.add(tag);

        giftCertificate = new GiftCertificate();
        giftCertificate.setId(TEST_ID);
        giftCertificate.setName(TEST_NAME);
        giftCertificate.setDescription(TEST_DESCRIPTION);
        giftCertificate.setPrice(TEST_PRICE);
        giftCertificate.setDuration(TEST_DURATION);
        giftCertificate.setTags(tags);

        tagDto = new TagDto();
        tagDto.setId(TEST_TAG_ID);
        tagDto.setName(TEST_TAG_NAME);

        dtoTags = new HashSet<>();
        dtoTags.add(tagDto);

        validGiftCertificateDto = new GiftCertificateDto();
        validGiftCertificateDto.setId(TEST_ID);
        validGiftCertificateDto.setName(TEST_NAME);
        validGiftCertificateDto.setDescription(TEST_DESCRIPTION);
        validGiftCertificateDto.setPrice(TEST_PRICE);
        validGiftCertificateDto.setDuration(TEST_DURATION);
        validGiftCertificateDto.setTags(dtoTags);

        giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificate);
    }

    @Test
    void getById() {
        when(giftCertificateDao.findById(TEST_ID)).thenReturn(Optional.of(giftCertificate));
        GiftCertificateDto actualGiftCertificateDto = giftCertificateService.getById(TEST_ID);

        GiftCertificateDto expectedCertificateDto = DtoMapper.certificateToDto(giftCertificate);
        Assertions.assertEquals(expectedCertificateDto, actualGiftCertificateDto);
    }

    @Test
    void create() {
        when(tagDao.findById(TEST_TAG_ID)).thenReturn(Optional.of(tag));
        when(giftCertificateDao.create(any())).thenReturn(giftCertificate);
        GiftCertificateDto actual = giftCertificateService.create(validGiftCertificateDto);

        assertEquals(TEST_NAME, actual.getName());
        assertEquals(TEST_DESCRIPTION, actual.getDescription());
        assertEquals(TEST_PRICE, actual.getPrice());
        assertEquals(TEST_DURATION, actual.getDuration());
    }


    @Test
    void notFoundCertificateById() {
        given(giftCertificateDao.findById(NOT_VALID_TAG_ID)).willReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> giftCertificateService.getById(NOT_VALID_TAG_ID));
    }

    @Test
    void deleteExpectNoSuchEntityException() {
        doThrow(new NoSuchEntityException()).when(giftCertificateDao).delete(NO_SUCH_CERTIFICATE_ID);
        Assertions.assertThrows(NoSuchEntityException.class, () -> giftCertificateService.delete(NO_SUCH_CERTIFICATE_ID));
    }
}