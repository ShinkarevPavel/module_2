package com.epam.esm.dao.impl;

import com.epam.esm.dbconfig.TestConfig;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@ActiveProfiles("dev")
class TagDaoImplTest {
    private static final int EXPECTED_LIST_SIZE = 4;
    private static final long EXPECTED_TAG_ID = 4L;
    private static final String EXPECTED_TAG_NAME = "Epam";
    private static final String CREATED_TAG_NAME = "newTag";


    private static Tag expectedTag;
    private TagDaoImpl tagDao;

    @BeforeAll
    static void prepare() {
        expectedTag = Tag.builder()
                .id(EXPECTED_TAG_ID)
                .name(EXPECTED_TAG_NAME)
                .build();
    }

    @Autowired
    public TagDaoImplTest(TagDaoImpl tagDao) {
        this.tagDao = tagDao;
    }

    @Test
    @Order(1)
    void create() {
        Tag actual = tagDao.create(Tag.builder()
                .name(CREATED_TAG_NAME).build());
        assertEquals(actual.getName(), CREATED_TAG_NAME);
        assertNotNull(actual.getId());
    }

    @Test
    @Order(2)
    void delete() {
        tagDao.delete(5l);
    }

    @Test ()
    void notExistTagById() {
        Optional<Tag> actual = tagDao.findById(5L);
        assertTrue(actual.isEmpty());
    }

    @Test ()
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
//        Tag tag = tagDao.findOrCreateTag(new Tag(EXPECTED_TAG_ID, EXPECTED_TAG_NAME));
//        assertEquals(tag, expectedTag);
    }
}