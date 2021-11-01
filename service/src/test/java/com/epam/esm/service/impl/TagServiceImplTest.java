package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.PageParameterDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.exception.UnacceptableRemoveEntityException;
import com.epam.esm.util.DtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TagServiceImpl.class)
class TagServiceImplTest {

    private static final long FIRST_TAG_ID = 112;
    private static final long SECOND_TAG_ID = 223;
    private static final long NEW_TAG_ID = 12;
    private static final String FIRST_TAG_NAME = "IT";
    private static final String SECOND_TAG_NAME = "HR";
    private static final String NEW_TAG_NAME = "Java";
    private TagDto firstTagDto;
    private TagDto secondTagDto;
    private Tag firstTag;
    private Tag secondTag;
    private Tag newTag;
    private List<TagDto> tagsDto;
    private List<Tag> tags;

    @Autowired
    private TagServiceImpl tagService;

    @MockBean
    private TagDaoImpl tagDao;

    @MockBean
    private UserDao userDao;

    @MockBean
    private PageParameterDto pageParameterDto;

    @BeforeEach
    void prepare() {
        firstTagDto = new TagDto();
        firstTagDto.setId(FIRST_TAG_ID);
        firstTagDto.setName(FIRST_TAG_NAME);

        secondTagDto = new TagDto();
        secondTagDto.setId(SECOND_TAG_ID);
        secondTagDto.setName(SECOND_TAG_NAME);

        firstTag = new Tag();
        firstTag.setId(FIRST_TAG_ID);
        firstTag.setName(FIRST_TAG_NAME);

        secondTag = new Tag();
        secondTag.setId(SECOND_TAG_ID);
        secondTag.setName(SECOND_TAG_NAME);

        newTag = new Tag();
        newTag.setId(NEW_TAG_ID);
        newTag.setName(NEW_TAG_NAME);

        tagsDto = new ArrayList<>();
        tagsDto.add(firstTagDto);
        tagsDto.add(secondTagDto);

        tags = new ArrayList<>();
        tags.add(firstTag);
        tags.add(secondTag);

    }

    @Test
    void getByName() {
        when(tagDao.findByName(firstTag.getName())).thenReturn(Optional.of(firstTag));
        TagDto actual = tagService.getByName(firstTag.getName());
        assertEquals(actual.getName(), firstTag.getName());
    }

    @Test
    void findById() {
        when(tagDao.findById(FIRST_TAG_ID)).thenReturn(Optional.of(firstTag));
        TagDto actual = tagService.getById(FIRST_TAG_ID);
        assertEquals(DtoMapper.dtoToTag(actual), firstTag);
    }

    @Test
    void getAll() {
        when(tagDao.findAll(any())).thenReturn(tags);
        List<TagDto> tags = tagService.getAll(pageParameterDto);
        assertEquals(tags.size(), 2);
    }

    @Test
    void getWidelyUsedTagWithHighestOrderCost() {
        when(tagDao.getWidelyUsedTagWithHighestOrderCost()).thenReturn(Optional.of(firstTag));
        TagDto tagDto = tagService.getWidelyUsedTagWithHighestOrderCost();
        assertEquals(tagDto.getName(), firstTag.getName());
    }

    @Test
    void testDeleteByIdExpectNoSuchEntityException() {
        when(tagDao.findById(FIRST_TAG_ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchEntityException.class, () -> tagService.delete(FIRST_TAG_ID));
    }

    @Test
    void testDeleteByIdExpectException() {
        when(tagDao.findById(FIRST_TAG_ID)).thenReturn(Optional.of(firstTag));
        doThrow(new UnacceptableRemoveEntityException()).when(tagDao).delete(FIRST_TAG_ID);
        Assertions.assertThrows(UnacceptableRemoveEntityException.class, () -> tagService.delete(FIRST_TAG_ID));
    }
}