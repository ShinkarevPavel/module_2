package com.epam.esm.util;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Builder
@Component
public class DtoMapper { // Todo Rename to DtoMapper and transfer TadConverter here

    public static GiftCertificateDto CertificateToDto(GiftCertificate giftCertificate) {
        return GiftCertificateDto.builder()
                .id(giftCertificate.getId())
                .name(giftCertificate.getName())
                .description(giftCertificate.getDescription())
                .duration(giftCertificate.getDuration())
                .price(giftCertificate.getPrice())
                .createDate(giftCertificate.getCreateDate())
                .lastUpdateDate(giftCertificate.getLastUpdateDate())
                .tags(giftCertificate.getTags().stream().map(DtoMapper::TagToDto).collect(Collectors.toList()))
                .build();
    }

    public static GiftCertificate dtoToCertificate(GiftCertificateDto giftCertificateDto) {
        return GiftCertificate.builder()
                .id(giftCertificateDto.getId() != null ? giftCertificateDto.getId() : null)
                .name(giftCertificateDto.getName())
                .description(giftCertificateDto.getDescription())
                .price(giftCertificateDto.getPrice())
                .duration(giftCertificateDto.getDuration())
                .createDate(giftCertificateDto.getCreateDate())
                .lastUpdateDate(giftCertificateDto.getLastUpdateDate())
                .tags(giftCertificateDto.getTags().stream().map(DtoMapper::dtoToTag).collect(Collectors.toList()))
                .build();
    }

    public static TagDto TagToDto(Tag tag) {
        TagDto tagDto = TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
        return tagDto;
    }

    public static Tag dtoToTag(TagDto tagDto) {
        Tag tag = Tag.builder()
                .id(tagDto.getId() != null ? tagDto.getId() : null)
                .name(tagDto.getName())
                .build();
        return tag;
    }
}