package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.impl.TagCertificateDaoImpl;
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
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GiftCertificateServiceImpl.class)
class GiftCertificateServiceImplTest {

    private final static long TEST_ID = 1;
    private final static long TEST_TAG_ID = 2;
    private final static long NOT_VALID_TAG_ID = 2052;
    private final static String TEST_TAG_NAME = "Test tag";
    private final static String TEST_NAME = "CertificateOne";
    private final static String TEST_DESCRIPTION = "certificate";
    private final static double TEST_PRICE = 15.12;
    private final static int TEST_DURATION = 10;

    @Autowired
    private GiftCertificateServiceImpl giftCertificateService;

    @MockBean
    private GiftCertificateDao giftCertificateDao;

    @MockBean
    private TagDao tagDao;

    @MockBean
    private TagCertificateDaoImpl tagCertificateDaoImpl;

    private Tag tag;
    private GiftCertificate giftCertificate;
    private TagDto tagDto;
    private GiftCertificateDto validGiftCertificateDto;
    private List<Tag> tags;
    private List<TagDto> dtoTags;
    private List<GiftCertificate> giftCertificates;


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

        tagDto = new TagDto();
        tagDto.setId(TEST_TAG_ID);
        tagDto.setName(TEST_TAG_NAME);

        dtoTags = new ArrayList<>();
        dtoTags.add(tagDto);

        validGiftCertificateDto = new GiftCertificateDto();
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
        given(giftCertificateDao.findById(TEST_ID)).willReturn(Optional.of(giftCertificate));
        GiftCertificateDto actualGiftCertificateDto = giftCertificateService.getById(TEST_ID);

        GiftCertificateDto expectedCertificateDto = DtoMapper.certificateToDto(giftCertificate);
        Assertions.assertEquals(expectedCertificateDto, actualGiftCertificateDto);
    }

    @Test
    void create() {
        given(giftCertificateDao.create(any())).willReturn(giftCertificate);
        given(tagDao.addCertificateTags(tags)).willReturn(tags);

        GiftCertificateDto actual = giftCertificateService.create(validGiftCertificateDto);

        assertEquals(TEST_NAME, actual.getName());
        assertEquals(TEST_DESCRIPTION, actual.getDescription());
        assertEquals(TEST_PRICE, actual.getPrice());
        assertEquals(TEST_DURATION, actual.getDuration());
    }


    @Test
    void notFoundCertificateByID() {
        given(giftCertificateDao.findById(NOT_VALID_TAG_ID)).willReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> giftCertificateService.getById(NOT_VALID_TAG_ID));
    }
}