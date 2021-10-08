package com.epam.esm.dao.impl;

import com.epam.esm.dbconfig.TestConfig;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Component
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
class TagDaoImplTest {
    private static final int EXPECTED_LIST_SIZE = 4;
    private static final long EXPECTED_TAG_ID = 4L;
    private static final String EXPECTED_TAG_NAME = "Epam";


    private Tag expectedTag;
    private TagDaoImpl tagDao;

    @Autowired
    public TagDaoImplTest(TagDaoImpl tagDao) {
        this.tagDao = tagDao;
    }

    @Test
    void create() {
    }

    @Test
    void findById() {
        Optional<Tag> byId = tagDao.findById(3l);
        assertTrue(byId.isPresent());
    }

    @Test
    void findAll() {
        List<Tag> tags = tagDao.findAll();
        assertEquals(EXPECTED_LIST_SIZE, tags.size());
    }
    @Test
    void findOrCreateTag() {
        expectedTag = new Tag(EXPECTED_TAG_ID, EXPECTED_TAG_NAME);
        Tag tag = tagDao.findOrCreateTag(new Tag(EXPECTED_TAG_ID, EXPECTED_TAG_NAME));
        assertEquals(tag, expectedTag);
    }

    @Test
    void addCertificateTags() {
    }
}