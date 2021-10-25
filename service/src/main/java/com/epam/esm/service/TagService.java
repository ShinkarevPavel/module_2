package com.epam.esm.service;

import com.epam.esm.dto.TagDto;


import java.util.List;

public interface TagService {

    TagDto create(TagDto tagDto);

    void delete(Long id);

    TagDto getById(Long id);

    TagDto getByName(String name);

    List<TagDto> getAll(int page, int size);

    TagDto getWidelyUsedTagWithHighestOrderCost();
}
