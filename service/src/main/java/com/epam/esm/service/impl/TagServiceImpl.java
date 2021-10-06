package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.DtoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Builder
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private TagDao tagDao;

    @Override
    public TagDto create(TagDto tagDto) {
        Tag tag = tagDao.create(DtoMapper.dtoToTag(tagDto));
        return DtoMapper.TagToDto(tag);
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Method is not supported");
    }

    @Override
    public TagDto getById(Long id) {
        return tagDao.findById(id)
                .map(DtoMapper::TagToDto)
                .orElseThrow(NoSuchEntityException::new);
    }

    @Override
    public List<TagDto> getAll() {
        List<Tag> tags = tagDao.findAll();
        return tags.stream()
                .map(DtoMapper::TagToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto getByName(String name) {
        return tagDao.findByName(name)
                .map(DtoMapper::TagToDto)
                .orElseThrow(NoSuchEntityException::new);
    }
}
