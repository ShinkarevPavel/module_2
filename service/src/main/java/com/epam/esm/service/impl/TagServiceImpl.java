package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PageParameterDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntryAlreadyExistsException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private TagDao tagDao;
    private UserDao userDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao, UserDao userDao) {
        this.tagDao = tagDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        if (tagDao.findByName(tagDto.getName()).isPresent()) {
            throw new EntryAlreadyExistsException("error_message.exist");
        }
        Tag tag = tagDao.create(DtoMapper.dtoToTag(tagDto));
        return DtoMapper.TagToDto(tag);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (tagDao.findById(id).isEmpty()) {
            throw new NoSuchEntityException();
        }
        tagDao.delete(id);
    }

    @Override
    public TagDto getById(Long id) {
        return tagDao.findById(id)
                .map(DtoMapper::TagToDto)
                .orElseThrow(NoSuchEntityException::new);
    }

    @Override
    public List<TagDto> getAll(PageParameterDto pageParameterDto) {
        List<Tag> tags = tagDao.findAll(DtoMapper.dtoToPageParameter(pageParameterDto));
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

    @Override
    public TagDto getWidelyUsedTagWithHighestOrderCost() {
        return tagDao.getWidelyUsedTagWithHighestOrderCost()
                .map(DtoMapper::TagToDto)
                .orElseThrow(NoSuchEntityException::new);
    }
}
