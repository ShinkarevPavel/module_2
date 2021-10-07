package com.epam.esm.service.impl;

import com.epam.esm.dao.TagCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntryAlreadyExistsException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.exception.NotAcceptableActionException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.DtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private TagDao tagDao;
    private TagCertificateDao tagCertificateDao;

    public TagServiceImpl(TagDao tagDao, TagCertificateDao tagCertificateDao) {
        this.tagDao = tagDao;
        this.tagCertificateDao = tagCertificateDao;
    }

    @Override
    public TagDto create(TagDto tagDto) {
        if (tagDao.findByName(tagDto.getName()).isPresent()) {
            throw new EntryAlreadyExistsException("error_message.exist");
        }
        Tag tag = tagDao.create(DtoMapper.dtoToTag(tagDto));
        return DtoMapper.TagToDto(tag);
    }

    @Override
    public void delete(Long id) {
        if (!tagDao.findById(id).isPresent()) {
            throw new NoSuchEntityException();
        }
        if (tagCertificateDao.isPresentRowByTadId(id)) {
            throw new NotAcceptableActionException("error_message.forbidden");
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
