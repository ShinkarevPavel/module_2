package com.epam.esm.util;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder
public class TagConverter {

    public static TagDto toDto(Tag tag) {
        TagDto tagDto = TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
        return tagDto;
    }

    public static Tag toEntity(TagDto tagDto) {
        Tag tag = Tag.builder()
                .id(tagDto.getId() != null ? tagDto.getId() : null)
                .name(tagDto.getName())
                .build();
        return tag;
    }
}
