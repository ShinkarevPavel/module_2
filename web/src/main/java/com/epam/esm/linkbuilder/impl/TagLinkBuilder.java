package com.epam.esm.linkbuilder.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class TagLinkBuilder extends AbstractBuilder<TagDto> {

    @Override
    public void addLinks(TagDto tagDto) {
        addIdLinks(TagController.class, tagDto, tagDto.getId(), SELF, DELETE);
    }
}
