package com.epam.esm.hateos.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import org.springframework.stereotype.Component;


@Component
public class TagLinkBuilder extends AbstractBuilder<TagDto> {

    @Override
    public void addLinks(TagDto tagDto) {
        addIdLinks(TagController.class, tagDto, tagDto.getId(), GET, DELETE);
    }
}
