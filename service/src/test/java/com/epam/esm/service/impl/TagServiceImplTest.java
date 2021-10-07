package com.epam.esm.service.impl;

import com.epam.esm.configuration.ServiceConfiguration;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.DtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
class TagServiceImplTest {

    private static final long FIRST_TAG_ID = 1;
    private static final long SECOND_TAG_ID = 2;
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

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDao tagDao;

    @Mock
    private DtoMapper dtoMapper;

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
        given(tagDao.findByName(firstTag.getName())).willReturn(Optional.of(firstTag));
        TagDto actual = tagService.getByName(firstTag.getName());
        Assertions.assertEquals(DtoMapper.dtoToTag(actual), firstTag);
    }
}